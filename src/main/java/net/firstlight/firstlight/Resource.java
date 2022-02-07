package net.firstlight.firstlight;

public interface Resource {
    String getPrefix();

    enum Type implements Resource {
        SOUND,
        OTHER,
        ANIMATION,
        GECKO_MODEL;

        @Override
        public String getPrefix() {
            switch (this) {
                case SOUND: return "sounds/";
                case ANIMATION: return "animations/";
                case GECKO_MODEL: return "geo/";
                case OTHER:
                default: return "";
            }
        }

        public enum Texture implements Resource {
            ITEM,
            BLOCK,
            ENTITY;

            @Override
            public String getPrefix() {
                switch (this) {
                    case ITEM: return "textures/item/";
                    case BLOCK: return "textures/block/";
                    case ENTITY: return "textures/entity/";
                    default: return "textures/";
                }
            }
        }

        public enum Model implements Resource {
            ITEM,
            BLOCK;

            @Override
            public String getPrefix() {
                switch (this) {
                    case ITEM: return "models/item/";
                    case BLOCK: return "models/block";
                    default: return "models/";
                }
            }
        }
    }
}
