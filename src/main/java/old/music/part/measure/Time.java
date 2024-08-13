package old.music.part.measure;

/**
 * contains all the possible time signatures with the amount of beats in a measure
 */
// TODO make not as enum, but record class, and have translation to ireal pro symbols in a properties file
public enum Time {
    T44 {
        @Override
        public int getBeats() {
            return 4;
        }
    }, T34 {
        @Override
        public int getBeats() {
            return 3;
        }
    }, T24 {
        @Override
        public int getBeats() {
            return 2;
        }
    }, T54 {
        @Override
        public int getBeats() {
            return 5;
        }
    }, T64 {
        @Override
        public int getBeats() {
            return 6;
        }
    }, T74 {
        @Override
        public int getBeats() {
            return 7;
        }
    }, T22 {
        @Override
        public int getBeats() {
            return 2;
        }
    }, T32 {
        @Override
        public int getBeats() {
            return 3;
        }
    }, T58 {
        @Override
        public int getBeats() {
            return 5;
        }
    }, T68 {
        @Override
        public int getBeats() {
            return 6;
        }
    }, T75 {
        @Override
        public int getBeats() {
            return 7;
        }
    }, T98 {
        @Override
        public int getBeats() {
            return 9;
        }
    }, T12 {
        @Override
        public int getBeats() {
            return 12;
        }
    };

    public abstract int getBeats();
}