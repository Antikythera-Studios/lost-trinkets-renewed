# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## 21.1.3 - 2026-05-30

### Added
  - Chinese (zh_cn) translation

### Fixed
  - Multiplayer crash when right-clicking air on servers without Lost Trinkets (Magneto C2S packet now skips when the server hasn't advertised the channel)
  - Dragon Breath trinket only producing a single smelted ingot from ores that drop multiple items (e.g. copper); output now scales with the input stack size
  - Book O' Enchanting now grants the equivalent of a max enchanting setup on modded enchanting tables (e.g. Apothic Enchanting) instead of always capping at level 30
  - Treble Hooks no longer rolls fishing loot when the rod hooks an entity (mobs, players, fish-mobs like Cod/Salmon) or when an empty rod is retrieved; only actual fish catches are multiplied

## 21.1.2 - 2026-04-19

### Fixed
  - Dedicated server crash
  - other mixin issues with Artifacts

## 21.1.1 - 2026-04-19

### Fixed
  - Neoforge Crash
  - Trinkets & probably some other mods. Thanks to unilock PR #1

## 21.1.0 - 2026-04-1

### Added
  - Support for multiloader(Forge/Fabric)
  - Ported to 1.21.1
