import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.{AmazonSQSAsync, AmazonSQSAsyncClientBuilder}
import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}
import connections.{S3Connector, S3ConnectorImpl}
import consumers.{SqsConsumer, SqsConsumerImpl}
import dao.{InMemRepository, Repository}
import services.{PokemonService, PokemonServiceImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Config]).toInstance(ConfigFactory.load())

    bind(classOf[SqsConsumer]).to(classOf[SqsConsumerImpl]).asEagerSingleton()
    bind(classOf[S3Connector]).to(classOf[S3ConnectorImpl])

    bind(classOf[Repository]).to(classOf[InMemRepository])

    bind(classOf[PokemonService]).to(classOf[PokemonServiceImpl])

    bind(classOf[AmazonSQSAsync]).toInstance(AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.EU_WEST_1).build())
  }
}
