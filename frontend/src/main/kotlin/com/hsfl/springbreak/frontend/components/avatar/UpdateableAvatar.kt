package com.hsfl.springbreak.frontend.components.avatar

import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import kotlinx.js.get
import mui.icons.material.Upload
import mui.material.*
import mui.system.sx
import org.w3c.dom.url.URL
import react.FC
import react.Props
import react.create
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.useState
import web.file.File

external interface UploadAvatarProps: Props {
    var size: Length
    var currentProfileImage: String
    var onProfileImageChanged: (File) -> Unit
}

val UploadAvatar = FC<UploadAvatarProps> { props ->
    var profileImage by useState<String>()
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.spaceEvenly
            alignItems = AlignItems.center
        }
        Badge {
            overlap = BadgeOverlap.circular
            anchorOrigin = object : BadgeOrigin {
                override var horizontal = BadgeOriginHorizontal.right
                override var vertical = BadgeOriginVertical.bottom
            }
            badgeContent = Tooltip.create {
                title = Typography.create { +"Profilbild hochladen" }
                IconButton {
                    sx {
                        backgroundColor = Color("white")
                        boxShadow = BoxShadow(
                            offsetX = 3.px,
                            offsetY = 3.px,
                            blurRadius = 3.px,
                            color = Color("lightgrey")
                        )
                        hover {
                            backgroundColor = Color("white")
                        }

                    }
                    component = ReactHTML.label
                    ReactHTML.input {
                        hidden = true
                        accept = "image/*"
                        type = InputType.file
                        onChange = {
                            if (it.target.files?.length != 0) {
                                it.target.files?.get(0)?.let { file ->
                                    profileImage = URL.createObjectURL(file)
                                    props.onProfileImageChanged(file)
                                }
                            }
                        }
                    }
                    Upload()
                }
            }
            Avatar {
                sx {
                    width = props.size
                    height = props.size
                }
                src = profileImage ?: props.currentProfileImage
            }
        }
    }
}