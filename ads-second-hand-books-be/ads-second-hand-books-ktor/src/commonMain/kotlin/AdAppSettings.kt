import org.akira.otuskotlin.ads.app.common.IAdAppSettings
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings

data class AdAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: AdCorSettings = AdCorSettings(),
    override val processor: AdProcessor = AdProcessor(corSettings)
) : IAdAppSettings
