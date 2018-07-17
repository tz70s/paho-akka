package com.sandinh.paho.akka

import akka.actor.ActorRef
import org.eclipse.paho.client.mqttv3.MqttMessage

/** TODO support wildcards subscription
  * TODO support Unsubscribe
  */
case class Subscribe(topic: String, ref: ActorRef, qos: Int = 0)

case class Unsubscribe(topic: String, ref: ActorRef)

case class SubscribeAck(subscribe: Subscribe, fail: Option[Throwable])

case class UnsubscribeAck(topic: String)

class Message(val topic: String, val payload: Array[Byte])

case object AckOwnerDeliveryComplete
case class DeliveryComplete(topic: String)

/** This class wrap params for publishing to mqtt.
  *
  * Send an instance of this class to the MqttPubSub actor for publishing:
  * {{{ pubsub ! Publish("some_topic", "12345".getBytes }}}
  */
class Publish(val topic: String, payload: Array[Byte], qos: Int) {
  def message() = {
    val msg = new MqttMessage(payload)
    msg.setQos(qos)
    msg
  }
}

object Publish {
  @inline def apply(topic: String, payload: Array[Byte], qos: Int = 2) =
    new Publish(topic, payload, qos)
}
