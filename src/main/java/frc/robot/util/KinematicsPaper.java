package frc.robot.util;

/**
 * A class full of things I don't understand
 * The paper is titled "Control of Wheeled Mobile Robots: An Experimental Overview"
 * It can be found here: https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf
 * I'm treating all 2D vectors as 3D vectors where the z element is zero.
 *
 * Units can technically be whatever you want, but all angles are in radians.
 * I will be using SI units in all applications.
 */

public class KinematicsPaper {

    public double eq5_2_vd(double targetVelX, double targetVelY) {
        /**
         * Implements equation 5.2, returning the magnitude of the velocity of the trajectory at time t.
         * given dx/dt and dy/dt, finds ds/dt
         */
        return Math.sqrt(targetVelX * targetVelX + targetVelY * targetVelY);
    }

    public double eq5_3_wd(double targetVelX, double targetAccX, double targetVelY, double targetAccY) {
        /**
         * Implements equation 5.3, returning the time derivative of the angle of the trajectory.
         */
        return (targetAccY * targetVelX - targetAccX * targetVelY) /
                (targetVelX * targetVelX + targetVelY * targetVelY);
    }

    public double eq5_4_thetad(double targetVelX, double targetVelY, int k) {
        /**
         * Implements equation 5.4, returning the angle of the trajectory.
         */
        return Math.atan2(targetVelY, targetVelX) + Math.PI * k;
    }

    public Vec eq5_5_error(Vec destination, Vec position) {
        /**
         * Implements equation 5.5, returning a vector containing e_1, e_2, and e_3
         */
        Vec dPositon = Vec.subtract(destination, position);
        double cos = Math.cos(position.getZ());
        double sin = Math.sin(position.getZ()); //TODO: Add matrix multiplication?
        Vec row1 = new Vec(cos, sin, 0);
        Vec row2 = new Vec(-sin, cos, 0);
        Vec row3 = new Vec(0, 0, 1);
        return new Vec(
                dPositon.dot(row1),
                dPositon.dot(row2),
                dPositon.dot(row3)
        );
    }

    public Vec eq5_6_vw(Vec error, Vec vw_dest, Vec u_input) {
        /** Implements equation 5.6, returning v and w. I do not know the purpose of these inputs, I think they are
         * intended to be used like the target linear and angular velocities (vw_dest). However, they are labeled v and
         * omega, instead of v_d nad omaga_d
         */
        Vec intermediary = new Vec(Math.cos(error.getZ()), 1, 0);
        return vw_dest.clone().hadamardProduct(intermediary).subtract(u_input);
    }

    public Vec eq5_7_edot(Vec error, Vec vw_dest, Vec u_input) {
        /**
         * Implements equation 5.7, returning the error dynamics of the system (idk what it does)
         * it is supposedly derived from tangent linearization of the state error, e (i think).
         */
        Vec inter1 = new Vec(0, vw_dest.getY(), 0);
        Vec inter2 = new Vec(-vw_dest.getY(), 0, 0);
        Vec inter3 = new Vec(0, Math.sin(error.getZ()), 0);
        Vec inter4 = new Vec(u_input.getX(), 0, u_input.getY());

        Vec e_dot = new Vec(inter1.dot(error), inter2.dot(error), 0);
        e_dot.add(inter3.multiply(vw_dest.getX()));
        e_dot.add(inter4);
        return e_dot;
    }

    public Vec eq5_8_uinput(Vec k_gains, Vec error, Vec vw_dest) {
        /**
         * Implements equation 5.8, which relates the inputs u_1 and u_2 to the state error and target velocity.
         */

        return new Vec(
                -k_gains.getX() * error.getX(),
                -k_gains.getY() * error.getY() * Math.signum(vw_dest.getX()) - k_gains.getZ() * error.getZ(),
                0
        );
    }

    public Vec eq5_9_kgains(Vec vw_dest, double damp_coef, double chr_eq_b) {
        /** Implements equation 5.9, which adds gain scheduling for the gains k_1, k_2, and k_3.
         * damp_coef is the damping coefficient (labeled Zeta in the paper)
         * chr_eq_b is the variable introduced in the gain scheduling equations (labeled b in the paper)
         */

        double k1 = 2.0 * damp_coef * Math.sqrt(vw_dest.getY() * vw_dest.getY()
                + chr_eq_b * vw_dest.getX() * vw_dest.getX());
        double k2 = chr_eq_b * Math.abs(vw_dest.getX());

        return new Vec(k1, k2, k1);
    }

    public Vec eq5_10_nonlincontroller(Vec pos, Vec dest, Vec vw_dest, Vec k_gains) {
        /** Implements equation 5.10, which details a nonlinear controller.
         *  pos: current position
         *  dest: destination
         *  vw_dest: target |velocity| and angular velocity
         *  k_gains: controller gains
         */

        double cos = Math.cos(pos.getZ());
        double sin = Math.sin(pos.getZ());
        Vec dpos = Vec.subtract(dest, pos);

        double v_result = vw_dest.getX() * Math.cos(dpos.getZ())
                + k_gains.getX() * (cos * dpos.getX() + sin * dpos.getY());
        double w_result = vw_dest.getY()
                + Math.signum(vw_dest.getX()) * k_gains.getY() * (cos * dpos.getX() - sin * dpos.getY())
                + k_gains.getZ() * dpos.getZ();
        return new Vec(v_result, w_result, 0);
    }

    public Vec eq5_11_ctrllaw(Vec vw_dest, Vec error, Vec k_gains) {
        /** Implements equation 5.11, returning inputs u_1 and u_2.
         * The paper calls this equation the "control law," but I don't know what that means.
         */

        double u1 = -k_gains.getX() * error.getX();
        double u2 = -k_gains.getY() * error.getY() * vw_dest.getX() * Math.sin(error.getZ()) / error.getZ()
                - k_gains.getZ() * error.getZ();
        return new Vec(u1, u2, 0);
    }

    public Vec eq5_12_unified_ctrllaw(Vec pos, Vec dest, Vec vw_dest, Vec k_gains) {
        /** Implements equation 5.12, which combines 5.5 and 5.6 with 5.11 to get something better (i think).
         * It's mostly equivalent to 5.11, so I've copied and pasted most of it.
         *  pos: current position
         *  dest: destination
         *  vw_dest: target |velocity| and angular velocity
         *  k_gains: controller gains
         *
         *  Damping coef is in (0, 1)
         *  b > 0
         *
         *  This equation actually defines a new version of k_2, but I didn't want to rewrite all of my code making use
         *  of k_gains, so I modified the part of this function that uses k_2.
         */

        double cos = Math.cos(pos.getZ());
        double sin = Math.sin(pos.getZ());
        Vec dpos = Vec.subtract(dest, pos);

        double v_result = vw_dest.getX() * Math.cos(dpos.getZ())
                + k_gains.getX() * (cos * dpos.getX() + sin * dpos.getY());
        double w_result = vw_dest.getY()
                + Math.signum(vw_dest.getX()) * k_gains.getY() * (cos * dpos.getX() - sin * dpos.getY()) * Math.sin(dpos.getZ()) / dpos.getZ()
                + k_gains.getZ() * dpos.getZ();
        return new Vec(v_result, w_result, 0);
    }

    public Vec eq5_15_dynamic_comp(Vec u_inputs, Vec pos, double integrator, double dt){
        /** Implements equation 5.15, which relates u_inputs to the desired linear and angular velocities of the robot
         * returns a vector containing Zeta-dot (acceleration), v (integrated acceleration, with v(0) = v_d(0), and w
         *
         * TODO: Turn v-w pair into motor inputs
         */

        double cos = Math.cos(pos.getZ()), sin = Math.sin(pos.getZ());
        double zeta_dot = u_inputs.dot(new Vec(cos, sin, 0));
        double v = integrator + zeta_dot * dt;
        double w = u_inputs.dot(new Vec(cos, -sin, 0)) / zeta_dot;
        return new Vec(zeta_dot, v, w);
    }

    public Vec eq5_18_feedback_controller(Vec pos, Vec vel, Vec dest_pos, Vec dest_vel, Vec dest_acc, Vec P, Vec D){
        /** Implements equations 5.18, which provides the inputs for trajectory following based on dynamic feedback
         * linearization.
         *  pos: Robot position
         *  vel: Robot velocity
         *  dest_pos: Desired position on trajectory
         *  dest_vel: .
         *  dest_acc: .
         *  P: 2-Vec containing proportional gains
         *  D: 2-Vec containing derivative gains
         */
        Vec dpos = Vec.subtract(dest_pos, pos).hadamardProduct(P);
        Vec dvel = Vec.subtract(dest_vel, vel).hadamardProduct(D);
        return dest_acc.clone().add(dpos).add(dvel);
    }

    public Vec eq4_1_wheel_velocities(Vec outputs, double radius, double width){
        /** Solves the system introduced in equation 4.1 to transform (linear velocity, angular velocity) to angular
         * velocities for the left and right wheels respectively.
         *
         * radius: wheel radius
         * width: wheelbase width
         */

        double v = outputs.getX(), w = outputs.getY();

        double wl = (v - width * w / 2) / radius;
        double wr = (v + width * w / 2) / radius;

        return new Vec(wl, wr, 0);
    }

}
