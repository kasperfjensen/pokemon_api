import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Config]).toInstance(ConfigFactory.load())
  }
}
