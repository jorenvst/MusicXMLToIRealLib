package core;

import core.converter.SongConverter;
import core.converter.irealpro.IRealProConverter;

public enum ToFormat {
    IREAL_PRO(new IRealProConverter());

    private final SongConverter converter;

    ToFormat(SongConverter converter) {
        this.converter = converter;
    }

    public SongConverter converter() {
        return converter;
    }
}
