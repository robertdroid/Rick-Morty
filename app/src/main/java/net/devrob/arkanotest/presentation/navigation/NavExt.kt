package net.devrob.arkanotest.presentation.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey) {
    add(screen)
}

fun NavBackStack<NavKey>.navigateBack() {
    if (isEmpty()) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.navigateBackTo(screen: NavKey) {
    if (isEmpty()) return
    if (screen !in this) return
    while(isNotEmpty() && last() != screen) removeLastOrNull()
}

fun NavBackStack<NavKey>.navigateAndReplace(screen: NavKey) {
    clear()
    add(screen)
}