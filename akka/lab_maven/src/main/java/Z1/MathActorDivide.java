package Z1;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class MathActorDivide extends AbstractBehavior<MathActor.MathCommandDivide> {
    private int operationCount = 0;

    public MathActorDivide(ActorContext<MathActor.MathCommandDivide> context) { super(context); }

    public static Behavior<MathActor.MathCommandDivide> create() { return Behaviors.setup(MathActorDivide::new); }

    @Override
    public Receive<MathActor.MathCommandDivide> createReceive(){
        return newReceiveBuilder()
                .onMessage(MathActor.MathCommandDivide.class, this::onMathCommandDivide)
                .build();
    }

    private Behavior<MathActor.MathCommandDivide> onMathCommandDivide(MathActor.MathCommandDivide mathCommandDivide) {
        int result = mathCommandDivide.firstNumber / mathCommandDivide.secondNumber;
        operationCount++;
        System.out.println("actorDivide: operations number: " + operationCount);
        mathCommandDivide.replyTo.tell(new MathActor.MathCommandResult(result));
        return this;
    }
}
