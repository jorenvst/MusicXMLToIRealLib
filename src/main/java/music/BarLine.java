package music;

public enum BarLine {
    REGULAR {
        @Override
        public String symbol(BarLinePosition pos) {
            return "|";
        }
    }, HEAVY_HEAVY {
        @Override
        public String symbol(BarLinePosition pos) {
            return pos == BarLinePosition.START? "[" : "]";
        }
    }, HEAVY_LIGHT {
        @Override
        public String symbol(BarLinePosition pos) {
            return pos == BarLinePosition.START? "[" : "]";
        }
    }, LIGHT_HEAVY {
        @Override
        public String symbol(BarLinePosition pos) {
            return "Z";
        }
    }, LIGHT_LIGHT {
        @Override
        public String symbol(BarLinePosition pos) {
            return pos == BarLinePosition.START? "[" : "]";
        }
    }, REPETITION {
        @Override
        public String symbol(BarLinePosition pos) {
            return pos == BarLinePosition.START? "{" : "}";
        }
    };

    public enum BarLinePosition {
        START, END
    }

    public abstract String symbol(BarLinePosition pos);
}
