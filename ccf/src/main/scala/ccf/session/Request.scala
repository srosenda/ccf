package ccf.session

import ccf.transport.Request

abstract class AbstractRequest {
  protected def request(s: Session, requestType: String, channelId: ChannelId, content: Option[Any]): Request = Request(
    Map("sequenceId" -> s.seqId.toString, "version" -> s.version.toString, "clientId" -> s.clientId.id.toString,
        "channelId" -> channelId.toString, "type" -> requestType),
    content
  )
}

abstract class SessionControlRequest extends AbstractRequest {
  protected def request(s: Session, channelId: ChannelId): Request = request(s, requestType, channelId, content(channelId))
  private def content(channelId: ChannelId) = Some(Map("channelId" -> channelId.toString))
  val requestType: String
}

object JoinRequest extends SessionControlRequest {
  def apply(s: Session, channelId: ChannelId): Request = request(s, channelId)
  val requestType = "channel/join"
}

object PartRequest extends SessionControlRequest  {
  def apply(s: Session, channelId: ChannelId): Request = request(s, channelId)
  val requestType = "channel/part"
}

object InChannelRequest extends AbstractRequest {
  def apply(s: Session, requestType: String, channelId: ChannelId, content: Option[Any]): Request =
    request(s, requestType, channelId, content)
}


