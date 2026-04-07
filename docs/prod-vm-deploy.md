# Prod VM Deployment

## Scope

This repo deploys `workspaceaggregationservice-sox` to the existing prod VM as a Docker container on the shared runtime host.

- runtime mode: Docker container on the VM
- active Spring profile: `prod`
- deploy trigger: PR comment command only

This production bootstrap does not auto-deploy on merge and does not introduce repo clone or `git pull` on the VM.

## Runtime contract on the VM

The deploy workflow publishes an immutable image to GHCR and then starts that exact image on the VM.

Container contract:

- container name: `workspaceaggregationservice-sox`
- bind address: `127.0.0.1:9082`
- restart policy: `unless-stopped`
- Docker network: `sitionix-prod`
- Spring profile: `prod`

The container consumes two env files on the VM:

- shared internal-auth secret file:
  - `/opt/sitionix/runtime/shared/prod-internal-auth.env`
- workspace-only runtime file:
  - `/opt/sitionix/runtime/workspaceaggregationservice-sox/shared/workspaceaggregationservice-sox.prod.env`

The deploy script writes:

- `SPRING_PROFILES_ACTIVE=prod`
- `ENVIRONMENT=prod`
- JDBC credentials for `wags_sox`
- Kafka bootstrap servers
- Kafka consumer group id

The shared internal-auth secret file must already exist on the VM before the first deploy.

## Database migration contract

Flyway migrations live in [db-migration](/Users/vladvinskevitch/Documents/Java/sitionix/workspaceaggregationservice-sox/db-migration).

The production DB command resolves its target from [db-model.yaml](/Users/vladvinskevitch/Documents/Java/sitionix/workspaceaggregationservice-sox/db-migration/db-model.yaml).

Comment command:

```text
/deploy db --name wags_sox --env prod
```

The committed model assumes:

- SSH tunnel from GitHub Actions to the prod VM
- loopback PostgreSQL access on the VM
- database name `wags_sox`
- username `wagssox_app`

## Service deploy contract

Comment command:

```text
/deploy service --name workspaceaggregationservice-sox --env prod
```

Workflow flow:

1. build and publish immutable image to GHCR
2. create a small release payload with:
   - VM deploy script
   - release manifest
   - non-secret release env
   - runtime secret env
3. upload the payload to the VM over SSH
4. run the VM deploy script
5. wait for local actuator readiness on `127.0.0.1:9082`
6. verify private readiness and health through an SSH tunnel

## Health and security contract

The service now exposes actuator health endpoints for deploy probes:

- `/wagssox/actuator/health`
- `/wagssox/actuator/health/readiness`
- `/wagssox/actuator/health/liveness`

These endpoints are excluded through `forge.security.server.excludes`.
This requires `forge-security` with the fixed public-exclude semantics.

## GitHub Environment contract

Expected GitHub Environment `prod` values for this repo:

Variables:
- `DEPLOY_VM_PORT`
  - optional
  - default SSH port: `22`

Repository vars:
- `MAVEN_REPOSITORY_USERNAME`

Secrets:
- `DEPLOY_VM_HOST`
- `DEPLOY_VM_USER`
- `DEPLOY_VM_SSH_PRIVATE_KEY`
- `GHCR_PULL_USERNAME`
- `GHCR_PULL_TOKEN`
- `WAGS_SOX_DB_PASSWORD`

Repository secrets:
- `MAVEN_REPOSITORY_TOKEN`

## VM prerequisites

The VM must already provide:

- Docker installed and usable by the deploy user
- the `sitionix-prod` Docker network, or permission for the deploy user to create it
- reachable `postgres:5432`
- reachable `kafka:9092`
- shared internal auth secret file:
  - `/opt/sitionix/runtime/shared/prod-internal-auth.env`
