# Workspace Aggregation Service Dev Deploy

`workspaceaggregationservice-sox` deploys to the `dev` GitHub Environment from `develop`, and supports comment-driven deploy commands for any environment that follows the same runtime contract.

## PR comment commands

Run DB migration for the current PR head:

```text
/deploy db --name wags_sox --env dev
```

Run service deploy for the current PR head:

```text
/deploy service --name workspaceaggregationservice-sox --env dev
```

## Required GitHub configuration

Environment-scoped values in GitHub Environment `dev`:

- `DEPLOY_VM_PORT` variable
- `DEPLOY_VM_HOST` secret
- `DEPLOY_VM_USER` secret
- `DEPLOY_VM_SSH_PRIVATE_KEY` secret
- `GHCR_PULL_USERNAME` secret
- `GHCR_PULL_TOKEN` secret
- `WAGS_SOX_DB_PASSWORD` secret

Repository-scoped values:

- `MAVEN_REPOSITORY_USERNAME` variable
- `MAVEN_REPOSITORY_TOKEN` secret

## Runtime prerequisites on the VM

The target VM must already provide:

- `/opt/sitionix/runtime/shared/dev-internal-auth.env`
- Docker network `sitionix-dev` or permission for the deploy script to create it
- PostgreSQL reachable as `postgres:5432`
- Kafka reachable as `kafka:9092`

## Environment contract

The auto-deploy workflow is fixed to `develop -> dev`.

The service deploy action itself is environment-driven:

- Spring profile is set to the GitHub Environment name
- `ENVIRONMENT` is set to the same value
- shared auth file path is resolved as `/opt/sitionix/runtime/shared/<env>-internal-auth.env`
- service env file path is resolved as `/opt/sitionix/runtime/workspaceaggregationservice-sox/shared/workspaceaggregationservice-sox.<env>.env`
- Docker network is resolved as `sitionix-<env>`

That means a new environment does not require workflow changes. Only environment-scoped config is added, and DB migration support also needs a matching `db-migration` environment entry.
