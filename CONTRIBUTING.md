# 🌱 Contributing

This guide is for developers interested in contributing to Lost Trinkets.

> [!NOTE]
> Contributions are welcome, but please [open an issue](https://github.com/Antikythera-Studios/lost-trinkets-renewed/issues/new/choose)
> before working on medium to large changes, so we can agree on the approach first.

## 📜 Code of Conduct

Please read our [Code of Conduct](./CODE_OF_CONDUCT.md) to understand the expected standards of behavior when
participating in this project.

## 📋 Prerequisites

- Linux, macOS, or Windows.
- A [Java JDK](https://adoptium.net/temurin/releases). The version depends on the branch:
    - **26.1.2**: [25](https://adoptium.net/temurin/releases?version=25)
    - **1.21.1**: [21](https://adoptium.net/temurin/releases?version=21)
    - **1.20.1**: [17](https://adoptium.net/temurin/releases?version=17)
- [git](https://git-scm.com/) for version control.
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  with the [Minecraft Development plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) (**optional
  but recommended**).
- A [Minecraft account](https://www.minecraft.net/) to test online features (**optional**).

## 🌿 Branches & project layout

Each supported Minecraft version has its own branch: `26.1.2`, `1.21.1` and `1.20.1`.
Open your pull request against the branch of the version you're working on. Fixes may be ported to the other branches
by the maintainers.

The mod is multiloader, built with [Architectury](https://docs.architectury.dev/):

| Module               | Contents                                                     |
|----------------------|--------------------------------------------------------------|
| `common`             | Shared code and resources, most changes go here              |
| `forge` / `neoforge` | Forge (1.20.1) or NeoForge (1.21.1 and 26.1.2) specific code |
| `fabric`             | Fabric specific code                                         |

## 🍴 Forking & cloning the repository

> [!TIP]
> If you're new to pull requests, [this video](https://youtu.be/8lGpZkjnkt4?si=X_OV0U6Jk3ox-DHY) is a good
> beginner-friendly guide.

- Fork the [GitHub repo](https://github.com/Antikythera-Studios/lost-trinkets-renewed) to your account. If you already
  have a fork, make sure it's up to date.

- Clone your fork and switch to the branch of the version you're working on:

    ```bash
    git clone git@github.com:YOUR_GITHUB_USERNAME_HERE/lost-trinkets-renewed.git
    cd lost-trinkets-renewed
    git checkout 1.21.1
    ```

- Add the upstream repo, so you can fetch updates from the main repository:

    ```bash
    git remote add upstream git@github.com:Antikythera-Studios/lost-trinkets-renewed.git
    ```

- Create a branch for your changes:

    ```bash
    git checkout -b YOUR_BRANCH_NAME_HERE
    ```

- Commit and push your changes:

    ```bash
    git add .
    git commit -m "YOUR_COMMIT_MESSAGE_HERE"
    git push origin YOUR_BRANCH_NAME_HERE
    ```

- GitHub will prompt you to open a pull request. If it doesn't, open
  [this link](https://github.com/Antikythera-Studios/lost-trinkets-renewed/pull/new).

## 🧪 Testing

Build both loaders:

```shell
./gradlew build
```

Run the game or a dedicated server on a specific loader (use `neoforge` instead of `forge` on 1.21.1 and 26.1.2):

```shell
./gradlew :fabric:runClient
./gradlew :forge:runClient
./gradlew :fabric:runServer
./gradlew :forge:runServer
```

## ⚙️ Development Notes

- Update [`CHANGELOG.md`](CHANGELOG.md) whenever you make changes, following
  [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).
- Keep all player-facing text in the lang files (`common/src/main/resources/assets/losttrinkets/lang`), never hardcoded
  in code. Only `en_us.json` needs to be updated, translations are handled separately.
- If a change affects the public `api` package that add-ons rely on, mention it in the changelog.

## 🧩 Code Style & Recommendations

### 1. Keep shared code loader independent

Put shared logic in `common`, and only loader specific code in the `forge`, `neoforge` and `fabric` modules.
Avoid loader specific APIs like `@OnlyIn` in common code, they make the project harder to maintain and port to newer
Minecraft versions.

### 2. Keep the public API stable

Add-ons build against `guivnf.losttrinkets.api`. Avoid breaking changes there, and if one can't be avoided, call it out
in the changelog.

### 3. Keep third-party compatibility optional and isolated

When adding compatibility with another mod (e.g., Curios, JEI), keep it an optional dependency and keep its imports
inside their own package. Prefer the mod's public API and use mixins as a last option. Mixins into other mods belong in
their own mixin config, registered only when that mod is loaded.

Make sure it doesn't spam the log, with or without the other mod installed.

### 4. Keep code modular

Avoid large methods that do everything. Keeping the trigger separate from the action makes the code easier to reuse,
fix and port.

### 5. Test in a production environment

Something that works in the dev environment may not work in a real instance. When changing mixins or networking, build
the JAR and test it in a fresh instance on both loaders, and on a dedicated server if multiplayer is involved.

### 6. Avoid magic numbers

```diff
- rect.left = 4.24264068712;
+ rect.left = 3.0 * Math.sqrt(2);
```

### 7. Keep comments short

Use Markdown javadoc (`///`) and keep it to the point. Say what the code is, not how it got there.

```java
/// Unlocks a random trinket for the player.
```

### 8. Don't hide programming bugs

Don't catch errors like `NullPointerException` or `IllegalArgumentException` to hide them, fix the cause instead.
A crash is easier to debug than a feature that silently stops working with no errors or logs.
Handling is only acceptable for expected failures, such as invalid config values or data from external sources.
