# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## 26.1.1 - 2026-11-21

### Added
  - Trinket hooks for add-ons (attacking, getting hurt, death, kills, drops, looting, effects, eating and mining).

### Fixed
  - Players on the same server blocking each other's trinket unlocks, which could leave the host never getting any.
  - Golden Horseshoe and Golden Tooth duplicating drops that looting doesn't affect, like Totems of Undying. They now add real looting levels.
  - `/losttrinkets clear` leaving trinket bonuses, like extra hearts, on the player.
  - Golden Melon and Lunch Bag not working on the last food item of a stack.
  - Creepo not stopping creepers from swelling on Fabric.

---

## 26.1.0 - 2026-07-13

### Added
  - New command `/losttrinkets unlock <targets> trinket <id>` to grant a specific trinket to players.
  - Spanish translations (Spain and Latin America).

### Changed
  - Ported to Minecraft 26.1.2 (new CalVer scheme) on Java 25, keeping NeoForge + Fabric multiloader support.
  - Updated to the unobfuscated 26.1 toolchain (Architectury Loom no-remap, Gradle 9) and refreshed all dependencies (NeoForge, Fabric API, Architectury, Curios, JEI, Forge Config API Port).
  - Reworked GUI rendering onto Minecraft 26.1's extract-based pipeline.
  - Updated the mod icon.
