package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteMetaEventMapperTest {

    @Mock
    private EventMapper<?, ?> firstEventMapper;

    @Mock
    private EventMapper<?, ?> secondEventMapper;

    private SiteMetaEventMapper siteMetaEventMapper;

    @BeforeEach
    void setUp() {
        this.siteMetaEventMapper = new SiteMetaEventMapper(List.of(this.firstEventMapper, this.secondEventMapper));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstEventMapper, this.secondEventMapper);
    }

    @Test
    void givenSupportedMapper_whenAsProjection_thenReturnMappedValue() {
        //given
        final Object payload = new Object();
        final WorkspaceSiteMeta expected = WorkspaceSiteMeta.builder().name("site").build();
        @SuppressWarnings("unchecked")
        final EventMapper<Object, WorkspaceSiteMeta> supportedEventMapper = (EventMapper<Object, WorkspaceSiteMeta>) this.secondEventMapper;

        when(this.firstEventMapper.supports(payload.getClass(), WorkspaceSiteMeta.class)).thenReturn(false);
        when(this.secondEventMapper.supports(payload.getClass(), WorkspaceSiteMeta.class)).thenReturn(true);
        when(supportedEventMapper.asProjection(payload)).thenReturn(expected);

        //when
        final WorkspaceSiteMeta actual = this.siteMetaEventMapper.asProjection(payload, WorkspaceSiteMeta.class);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.firstEventMapper).supports(payload.getClass(), WorkspaceSiteMeta.class);
        verify(this.secondEventMapper).supports(payload.getClass(), WorkspaceSiteMeta.class);
        verify(supportedEventMapper).asProjection(payload);
    }

    @Test
    void givenUnsupportedMapperList_whenAsProjection_thenThrowIllegalArgumentException() {
        //given
        final Object payload = new Object();

        when(this.firstEventMapper.supports(payload.getClass(), SiteMetaUpdate.class)).thenReturn(false);
        when(this.secondEventMapper.supports(payload.getClass(), SiteMetaUpdate.class)).thenReturn(false);

        //when //then
        assertThatThrownBy(() -> this.siteMetaEventMapper.asProjection(payload, SiteMetaUpdate.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(payload.getClass().getName())
                .hasMessageContaining(SiteMetaUpdate.class.getName());
        verify(this.firstEventMapper).supports(payload.getClass(), SiteMetaUpdate.class);
        verify(this.secondEventMapper).supports(payload.getClass(), SiteMetaUpdate.class);
    }
}
