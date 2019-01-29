import edu.wpi.first.wpilibj.command.Command;

public class LiftCommand extends Command
{
    private int final hatchNumber;
    private double final finalHeight;

    public LiftCommand(double finalHheight)
    {
        this.finalHeight = finalHeight;
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void execute()
    {

    }
}