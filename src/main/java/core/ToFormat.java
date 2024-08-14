package core;

import core.converter.SongConverter;
import core.converter.irealpro.IRealProConverter;

public enum ToFormat {
    IREAL_PRO(new IRealProConverter());

    private final SongConverter converter;

    /**
     * used for determining what implementation to use to convert a Song to an Exportable
     * @param converter the SongConverter implementation that should be used
     */
    ToFormat(SongConverter converter) {
        this.converter = converter;
    }

    /**
     * gets the SongConverter implementation
     * @return the SongConverter implementation
     */
    public SongConverter converter() {
        return converter;
    }
}
