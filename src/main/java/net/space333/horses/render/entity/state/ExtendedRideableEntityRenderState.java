package net.space333.horses.render.entity.state;

public interface ExtendedRideableEntityRenderState {
    void horses$setId(int id);
    int horses$getId();
    void horses$setPlayerPassenger(boolean playerPassenger);
    boolean horses$isPlayerPassenger();
}
