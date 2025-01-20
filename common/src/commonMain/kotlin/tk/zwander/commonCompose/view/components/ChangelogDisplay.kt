package tk.zwander.commonCompose.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.nanihadesuka.compose.ColumnScrollbarNew
import my.nanihadesuka.compose.ScrollbarSelectionMode
import tk.zwander.common.data.changelog.Changelog
import tk.zwander.common.util.invoke
import tk.zwander.commonCompose.util.ThemeConstants
import tk.zwander.commonCompose.util.toRichHtmlString
import tk.zwander.samloaderkotlin.resources.MR

@Composable
internal fun ChangelogDisplay(
    changelog: Changelog?,
    border: BorderStroke = CardDefaults.outlinedCardBorder(),
) {
    val scrollState = rememberScrollState()

    OutlinedCard(
        modifier = Modifier.padding(vertical = 4.dp)
            .heightIn(max = 500.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
        border = border,
    ) {
        ColumnScrollbarNew(
            state = scrollState,
            thumbColor = ThemeConstants.Colors.scrollbarUnselected,
            thumbSelectedColor = ThemeConstants.Colors.scrollbarSelected,
            alwaysShowScrollBar = true,
            padding = ThemeConstants.Dimensions.scrollbarPadding,
            thickness = ThemeConstants.Dimensions.scrollbarThickness,
            selectionMode = ScrollbarSelectionMode.Disabled,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(8.dp),
            ) {
                val infoItems by derivedStateOf {
                    val list = mutableListOf<String>()

                    changelog?.relDate?.let { relDate ->
                        list.add(MR.strings.release(relDate))
                    }

                    changelog?.secPatch?.let { secPatch ->
                        list.add(MR.strings.security(secPatch))
                    }

                    list
                }

                if (infoItems.isNotEmpty()) {
                    Text(
                        text = infoItems.joinToString("  •  "),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                if (changelog?.notes != null) {
                    SelectionContainer {
                        Text(
                            text = changelog.notes.toRichHtmlString(),
                            lineHeight = (LocalTextStyle.current.fontSize.value + 2).sp,
                        )
                    }
                }
            }
        }
    }
}
