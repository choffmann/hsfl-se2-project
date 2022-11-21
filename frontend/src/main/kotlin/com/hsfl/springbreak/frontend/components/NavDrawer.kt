package com.hsfl.springbreak.frontend.components

import com.hsfl.springbreak.frontend.utils.color
import csstype.FontWeight
import csstype.number
import mui.icons.material.*
import mui.material.*
import mui.material.List
import mui.system.Box
import mui.system.sx
import org.w3c.workers.RegistrationOptions
import react.FC
import react.PropsWithChildren
import react.useState



val NavDrawer = FC<PropsWithChildren> {
    val isAuthorized by useState(true)
    Drawer {
        sx {
            flexShrink = number(0.0)
            width = drawerWidth
        }
        variant = DrawerVariant.permanent
        anchor = DrawerAnchor.left
        Toolbar()
        if (isAuthorized) {
            List {
                ListItemButton {
                    ListItemAvatar {
                        Avatar { +"RH" }
                    }
                    ListItemText {
                        Typography {
                            sx { fontWeight = FontWeight.bold }
                            +"Ryan Hughes"
                        }
                        Typography {
                            color = "text.secondary"
                            +"ryan.hughes@mail-address.com"
                        }
                    }
                }
                Divider()
                ListItemButton {
                    ListItemIcon {
                        Favorite()
                    }
                    ListItemText {
                        Typography { +"Favoriten" }
                    }
                }
                ListItemButton {
                    ListItemIcon {
                        Category()
                    }
                    ListItemText {
                        Typography { +"Kategorien" }
                    }
                }
                ListItemButton {
                    ListItemIcon {
                        Book()
                    }
                    ListItemText {
                        Typography { +"Meine Rezepte" }
                    }
                }
                ListItemButton {
                    ListItemIcon {
                        Add()
                    }
                    ListItemText {
                        Typography { +"Rezept erstellen" }
                    }
                }
            }
        } else {
            List {
                ListItemButton {
                    ListItemIcon {
                        Login()
                    }
                    ListItemText {
                        Typography { +"Anmelden" }
                    }
                }
                Divider()
                ListItemButton {
                    ListItemIcon {
                        Category()
                    }
                    ListItemText {
                        Typography { +"Kategorien" }
                    }
                }
            }
        }
    }
}