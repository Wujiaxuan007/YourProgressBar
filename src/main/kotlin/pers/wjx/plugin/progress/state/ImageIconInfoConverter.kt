package pers.wjx.plugin.progress.state

import com.intellij.util.IconUtil
import com.intellij.util.xmlb.Converter
import pers.wjx.plugin.progress.common.Icons
import pers.wjx.plugin.progress.common.InvokeUtils
import pers.wjx.plugin.progress.common.Notification
import pers.wjx.plugin.progress.common.ProgressBarBundle
import java.io.File

/**
 * @author wjx
 */
class ImageIconInfoConverter : Converter<ImageIconInfo>() {
    override fun toString(value: ImageIconInfo): String? {
        return value.path
    }

    override fun fromString(value: String): ImageIconInfo {
        if (!File(value).exists()) {
            InvokeUtils.invokeLater(null) {
                Notification.showWarning(ProgressBarBundle.message("img.cannot.found.use.default", value), null)
                ProgressBarSettingState.getInstance().useDefaultIcon = true
            }
            return ImageIconInfo(value, Icons.PANDA, Icons.PANDA_LEFT)
        }
        val icon = Icons.cropAndResizeIcon(value)
        return ImageIconInfo(value, icon, IconUtil.flip(icon, true))
    }
}