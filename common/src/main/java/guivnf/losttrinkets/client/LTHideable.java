package guivnf.losttrinkets.client;

/**
 * Duck interface mixed into {@code EntityRenderState} so the "Tha Ghost fully hides an invisible
 * wearer" flag can be carried from render-state extraction (where the entity is available) to the
 * submit/render step (where in MC 26.1 only the render state is available).
 */
public interface LTHideable {
    boolean losttrinkets$isHidden();

    void losttrinkets$setHidden(boolean hidden);
}
