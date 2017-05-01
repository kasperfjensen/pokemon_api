import javax.inject.Inject
import play.api.http.HttpFilters
import filters.LoggingFilter

class Filters @Inject() ( loggingFilter: LoggingFilter) extends HttpFilters {
  def filters = Seq( loggingFilter)
}