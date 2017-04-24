package de.frosner.broccoli.controllers

import de.frosner.broccoli.controllers.OutgoingWsMessageType._
import de.frosner.broccoli.models.{AboutInfo, InstanceWithStatus, Template}
import Template.templateApiWrites
import InstanceWithStatus.instanceWithStatusWrites
import play.api.libs.json._

case class OutgoingWsMessage(messageType: OutgoingWsMessageType, payload: Object)

object OutgoingWsMessage {

  implicit val outgoingWsMessageWrites: Writes[OutgoingWsMessage] = Writes {
    case OutgoingWsMessage(ListTemplatesMsg, payload: Seq[Template]) =>
      wrap(ListTemplatesMsg, Json.toJson(payload))
    case OutgoingWsMessage(ListInstancesMsg, payload: Seq[InstanceWithStatus]) =>
      wrap(ListInstancesMsg, Json.toJson(payload))
    case OutgoingWsMessage(AboutInfoMsg, payload: AboutInfo) =>
      wrap(AboutInfoMsg, Json.toJson(payload))
    case OutgoingWsMessage(ErrorMsg, payload: String) =>
      wrap(ErrorMsg, Json.toJson(payload))
  }

  private def wrap(messageType: OutgoingWsMessageType, payload: JsValue): JsValue = JsObject(Map(
    "messageType" -> Json.toJson(messageType),
    "payload" -> payload
  ))

}
