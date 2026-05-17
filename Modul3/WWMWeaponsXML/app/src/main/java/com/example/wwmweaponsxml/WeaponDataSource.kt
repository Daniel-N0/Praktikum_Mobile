package com.example.wwmweaponsxml

object WeaponDataSource {
    val dummyWeapons = listOf(
        Weapon(
            id = 1,
            name = "Sword",
            type = "Role - Balanced",
            description = "Sword is the all-rounder weapon for wanderers who value flexibility above all else. Its move set flows smoothly between offense and defense with clean combos, approachable timings, and just enough utility to slot into most builds without drama. In Where Winds Meet it is a strong recommendation for new players who want to learn core systems while still having a weapon that scales comfortably into late-game content.",
            imageResId = R.drawable.sword,
            infoUrl = "https://wherewindsmeet.org/guides/weapons/sword"
        ),
        Weapon(
            id = 2,
            name = "Dual Blades",
            type = "Role - Assassin",
            description = "Dual Blades turn you into a darting close-range menace. Their reach is short, but the animation speed and cancel windows let you slip in, lock enemies down, and delete lone targets before they can respond. In Where Winds Meet they are ideal for players who enjoy aggressive duels, risky dash-ins, and playing the executioner whenever a boss is staggered.",
            imageResId = R.drawable.dual_blades,
            infoUrl = "https://wherewindsmeet.org/guides/weapons/dual-blades"
        ),
        Weapon(
            id = 3,
            name = "Spear",
            type = "Role - Bleed",
            description = "Spear offers classic reach and control, letting you decide exactly when enemies are allowed to step into danger. Its thrusts and sweeps cover generous arcs, poking safely in PVE while still giving you tools to contest space in duels. In Where Winds Meet it works well for steady frontline builds that prefer structured fights, zone control, and reliable answers to mobile targets.",
            imageResId = R.drawable.spear,
            infoUrl = "https://wherewindsmeet.org/guides/weapons/spear"
        ),
        Weapon(
            id = 4,
            name = "Rope Dart",
            type = "Role - Mobile Control / Picks",
            description = "A stylish mix of grapple and execution tool: yank enemies off balance from mid-range, instantly close gaps, or protect allies with pulls and repositioning. It rewards strong movement reading, creative positioning, and the instinct to turn escape attempts into losses.",
            imageResId = R.drawable.rope_dart,
            infoUrl = "https://wherewindsmeet.org/guides/weapons/rope-dart"
        ),
        Weapon(
            id = 5,
            name = "Umbrella",
            type = "Role - Parry/Counter",
            description = "It looks delicate, but hides serious defense and counterplay: glide through projectiles, angle guards, open the umbrella, and flow into strikes that punish overextensions. It fits reactive players who want to weave mobility, parries, and style into an elegant defensive DPS weapon.",
            imageResId = R.drawable.umbrella,
            infoUrl = "https://wherewindsmeet.org/guides/weapons/umbrella"
        )
    )
}