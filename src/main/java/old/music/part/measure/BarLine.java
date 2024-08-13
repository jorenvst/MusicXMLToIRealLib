package old.music.part.measure;

/**
 * each constant represents a type of barline
 * depending on its position in the measure, the symbol should be left or right
 */
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

    /**
     * used for telling if a barline is placed left or right in the measure
     */
    public enum BarLinePosition {
        START, END
    }

    /**
     *
     * @param pos the position (left or right) determines if the symbol should be facing left or right (irrelevant for symmetrical barlines like '|')
     * @return the symbol that depicts this type of barline
     */
    public abstract String symbol(BarLinePosition pos);
}
