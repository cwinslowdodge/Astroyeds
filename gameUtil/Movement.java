package package1.game.gameUtil;

public class Movement {

    public double x;
    public double y;

    public Movement(double degree) {
        this.x = Math.cos(degree);
        this.y = Math.sin(degree);
    }

    public Movement(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Movement(Movement move){
        this.x = move.x;
        this.y = move.y;
    }

    public void addSpeed(Movement speed){
        this.x = this.x + speed.x;
        this.y = this.y + speed.y;
    }

    public Movement scale(double factor){
        this.x *= factor;
        this.y *= factor;
        return this;
    }

    public Movement add(Movement move){
        this.x += move.x;
        this.y += move.y;
        return this;
    }

    public double getShipMagnitude() {
        return (x * x + y * y);
    }

    public Movement controlSpeed() {
        double magnitude = getShipMagnitude();
        if (magnitude != 0.0f && magnitude != 1.0f) {
            magnitude = Math.sqrt(magnitude);
            this.x /= magnitude;
            this.y /= magnitude;
        }
        return this;
    }

    public double getDistanceToSquared(Movement mvt) {
        double dx = this.x - mvt.x;
        double dy = this.y - mvt.y;
        return (dx * dx + dy * dy);
    }
}
