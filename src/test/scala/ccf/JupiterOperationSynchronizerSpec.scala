package ccf

import ccf.messaging.ConcurrentOperationMessage
import ccf.operation.Operation
import org.specs.Specification
import org.specs.mock.Mockito

case class TestOperation() extends Operation()
case class TestNoOperation() extends TestOperation()

object JupiterOperationSynchronizerSpec extends Specification with Mockito {
  val transformer = mock[JupiterTransformer[TestOperation]]
  val synchronizer = new JupiterOperationSynchronizer(true, transformer)

  "Any Jupiter operation synchronizer" should {
    "start indexing operations from zero" in {
      val msg = synchronizer.createLocalOperation(TestNoOperation())
      msg.localMessage must equalTo(0)
      msg.expectedRemoteMessage must equalTo(0)
    }

    "increment the local operation index on each created operation" in {
      synchronizer.createLocalOperation(TestNoOperation()).localMessage must equalTo(0)
      synchronizer.createLocalOperation(TestNoOperation()).localMessage must equalTo(1)
      synchronizer.createLocalOperation(TestNoOperation()).localMessage must equalTo(2)
      synchronizer.createLocalOperation(TestNoOperation()).localMessage must equalTo(3)
    }

    "increment the expected remote operation index on each received operation" in {
      val msg1 = ConcurrentOperationMessage[TestOperation](TestNoOperation(), 0, 0)
      val msg2 = ConcurrentOperationMessage[TestOperation](TestNoOperation(), 1, 0)
      val msg3 = ConcurrentOperationMessage[TestOperation](TestNoOperation(), 2, 0)
      synchronizer.receiveRemoteOperation(msg1)
      synchronizer.expectedRemoteMessage must equalTo(1)
      synchronizer.receiveRemoteOperation(msg2)
      synchronizer.expectedRemoteMessage must equalTo(2)
      synchronizer.receiveRemoteOperation(msg3)
      synchronizer.expectedRemoteMessage must equalTo(3)
    }
  }
}