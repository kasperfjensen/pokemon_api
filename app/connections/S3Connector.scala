package connections

import javax.inject.Inject

import com.typesafe.config.Config


trait S3Connector {
  def downloadFile() : List[String]
}


class S3ConnectorImpl @Inject() (config: Config) extends S3Connector {


  import com.amazonaws.auth.BasicAWSCredentials
  import com.amazonaws.services.s3.AmazonS3Client

  val s3 = new AmazonS3Client(new BasicAWSCredentials(config.getString("aws.username"), config.getString("aws.secret")))

  override def downloadFile(): List[String] = {
    import com.amazonaws.services.s3.model.GetObjectRequest
    import java.io.BufferedReader
    import java.io.InputStreamReader
    val s3object = s3.getObject(new GetObjectRequest("jpmedier-dev-datamatiker", "pokemon.txt"))

    val reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent))
    val strs = Stream.continually(reader.readLine()).takeWhile(_ != null)
    strs.drop(1).toList
  }

}
