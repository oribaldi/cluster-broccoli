package de.frosner.broccoli.controllers

import de.frosner.broccoli.controllers.OutgoingWsMessageType.OutgoingWsMessageType
import play.api.libs.json.{JsString, Reads, Writes}

object OutgoingWsMessageType extends Enumeration {

  type OutgoingWsMessageType = Value

  val ListTemplatesMsg = Value("listTemplates")
  val ListInstancesMsg = Value("listInstances")
  val AboutInfoMsg = Value("aboutInfo")
  val ErrorMsg = Value("error")
  val NotificationMsg = Value("notification")
  val InstanceCreationSuccessMsg = Value("addInstanceSuccess")
  val InstanceCreationFailureMsg = Value("addInstanceError")
  val InstanceDeletionSuccessMsg = Value("deleteInstanceSuccess")
  val InstanceDeletionFailureMsg = Value("deleteInstanceError")
  val InstanceUpdateSuccessMsg = Value("updateInstanceSuccess")
  val InstanceUpdateFailureMsg = Value("updateInstanceError")

  implicit val webSocketMessageTypeWrites: Writes[OutgoingWsMessageType] = Writes(value => JsString(value.toString))

}