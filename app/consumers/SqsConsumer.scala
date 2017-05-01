package consumers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.sqs.{Ack, SqsSourceSettings}
import akka.stream.alpakka.sqs.scaladsl.{SqsAckSink, SqsSource}
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.google.inject.Inject
import com.typesafe.config.Config

import services.PokemonService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait SqsConsumer

class SqsConsumerImpl @Inject() (config: Config, sqsClient: AmazonSQSAsync, service: PokemonService)
  extends SqsConsumer {

  private implicit val system = ActorSystem()
  private implicit val mat = ActorMaterializer()

  private val waitTimeSeconds = config.getInt("aws.sqs.consumer.waitTimeSeconds")
  private val maxBufferSize = config.getInt("aws.sqs.consumer.maxBufferSize")
  private val maxBatchSize = config.getInt("aws.sqs.consumer.maxBatchSize")
  private val sqsSourceSettings = SqsSourceSettings(waitTimeSeconds, maxBufferSize, maxBatchSize)
  private val parallelism = maxBufferSize / maxBatchSize

  private val queueUrl = config.getString("aws.sqs.consumer.url")

  SqsSource(queueUrl, sqsSourceSettings)(sqsClient)
    .mapAsync(parallelism) { message =>
      service.handleNewFile(message)
      Future(message, Ack())
    }
    .runWith(SqsAckSink(queueUrl)(sqsClient))
}
