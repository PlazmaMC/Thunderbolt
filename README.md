<div align="center">

# Thunderbolt

### A Server platform with features too experimental to implement into Plazma

[![Discord](https://img.shields.io/discord/1083716853928558652?style=for-the-badge&logo=discord&logoColor=ffffff&label=DISCORD&color=5865F2)](https://discord.gg/MmfC52K8A8)
[![License](https://img.shields.io/github/license/PlazmaMC/Thunderbolt?style=for-the-badge&logo=github&logoColor=ffffff)](LICENSE.md)
[![Stargazers](https://img.shields.io/github/stars/PlazmaMC/Thunderbolt?label=stars&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9bxSIVh1YQcYhQnSyIiuimVShChVArtOpgcukXNGlIUlwcBdeCgx+LVQcXZ10dXAVB8APE0clJ0UVK/F9SaBHjwXE/3t173L0D/PUyU82OMUDVLCOViAuZ7KrQ9YogwujDEGYkZupzopiE5/i6h4+vdzGe5X3uz9Gj5EwG+ATiWaYbFvEG8dSmpXPeJ46woqQQnxOPGnRB4keuyy6/cS447OeZESOdmieOEAuFNpbbmBUNlXiSOKqoGuX7My4rnLc4q+Uqa96TvzCU01aWuU5zEAksYgkiBMioooQyLMRo1UgxkaL9uId/wPGL5JLJVQIjxwIqUCE5fvA/+N2tmZ8Yd5NCcaDzxbY/hoGuXaBRs+3vY9tunACBZ+BKa/krdWD6k/RaS4seAb3bwMV1S5P3gMsdoP9JlwzJkQI0/fk88H5G35QFwrdA95rbW3Mfpw9AmrpK3gAHh8BIgbLXPd4dbO/t3zPN/n4Ax9dyyerighsAAAAGYktHRAAAAAAAAPlDu38AAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfmCBMVNjtc7/hFAAABIElEQVQ4y62SzS5DURSFv6smXkAUCRU0UdKYGNTPyCsYYOYFGGi8Ao9QM0PxCh6CgQ4qfiLpBFEjdKCfySaXtDch1uScs9Ze62TvcyAD6o66zV+gjqpvalsd61XXl5GxBySx3/3t7UPqi1pTD9VXdaRbbZIyDQLTwBSwBqzGGaABnAInwCXQSJLk/tO4orb8jra6nwo/CC6NlrqMOq421Y5aVSfUXJe2cqFVo7b5NdwIuVaf1IWM2cyrD+qdOvlTLERIS53pYi6FdqMWet2wGP1tdNE2Q1vK+gfDsdbDlFfzwV3Ems8KmAXegcd4hSvgVq0Bz6GV0ob+HgF1YAA4Cn4LWA9tLusHnscTHavFFF8MrqOeZQVU1HKGXlYr/Cc+AKuOI2h/Jrf7AAAAAElFTkSuQmCC&style=for-the-badge&color=green)](https://github.com/PlazmaMC/Thunderbolt/stargazers)

[![Upstream Status](https://img.shields.io/github/actions/workflow/status/PlazmaMC/Thunderbolt/upstream.yml?label=upstream&logo=data%3Aimage%2Fpng%3Bbase64%2CiVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAYAAADimHc4AAAACXBIWXMAAAsTAAALEwEAmpwYAAAGD0lEQVR4nO2dW4hVVRiAl5OjCFoMlpYx2WXMXjShi5FFTxFhhmFlQxlRU0kEXQiJorKIMoIiIoJegiiK8pKWZBTUg0ZpaHYhslBL8TKVTWWO00x98XP%2BicNhzt5rnb3P2WvtWd%2FjzDl7r%2FX%2F6%2FrfjjGRSCQSiUQikUgkEolEIpGIJwDHAHOA24CngTXA18Bu4BDwN3AUOAh8D3wOrAOeBLqBWUB70f0ICuBE4E7gXaCP7PwBrARuAqYU3T8vAdqB64EPgSGaxz%2F6joUyu8xoBzgOWAbsofXs0ndPNKMNoA24EThA8ezTPWZ0zAjgIt1IfWMbMM%2BUfJ1f3uQ1Po894rnSnZyAU3WEhcInwGmmDMi0BnozCGMA2KT3ADlKXgycAnQAY4HxwAnA6fouWc%2BfBzYCgxne%2BwtwgQkZ4Fq9KLlyBHgDWARMyPD%2BDr2QvamXNlf%2BAuabENGOu47AvcD9wOQmtOck4FG9NbsgfbjBhARwneNm26eCn9CCtk0EHtdZ5qKE%2BSEdM12WndVifiignbKPvOfQzsPA%2BSaA006vw%2Fq6pOD2jgHucRgw0rfpxkeAccBWBzPAHOMJMrIdBs4q4yNqArbhG2Ca8QxgBrDTov0yW8YaD9d9m013O3C88RRgmoUS%2Fm3FYcHVafKVhfB%2FAk42ngOcCfyc0I9txieApZYb7mwTCMBc4M8R%2BiGz%2FHLjC8CxlhebHhMYVDZmcXEO8wOwwPgEcJ%2BF8FebgAGmAp3GU%2FPyjxZLj5%2Fn5tBRH24aDxTdztICfGBhzp1UdDtLiYR3WJz7Hyy6naUFuCNF%2BGJ7n1p0O0uLRp8l8bYpIcAkYAXwKfAl8EzLg700pETCApPoNiUD6KpjppCTYFczXjimzt%2FPThH%2BULVHS00VfhmvGhO%2BeOvqsTc3JWgw7AagXx3i7wBnVf2%2FJ0UBn%2BnnZgLr9RniUfoYOM%2BUT%2Fj5KUGFP5IN5Dexj%2BhnniKZF9WWIt%2BppT8kJWAv%2FHyUoCM%2FyW8rgl2V0oiXU6KaPzLlFH42JejmKiM0CRHsfrIx6HscJo0LP7MSXCIFGkWU3GbKK%2FzGlaAZKM1mnSm%2F8BtTAnBGnc0zL%2BTZM8zoEH7DSpibU2rQiJu48RAqUXPNTBLZ4xT71AQleCt8AXid5vOacSFHJfgu%2FLYWHT4O17MuNFMJXgu%2FKqBMEjOy8Kve%2BNNMNO4mmQxK8F74wwBbEvoxoCmzafGtC1KUsMU0SgNKCEb4AnBZnVkwoIJ9KaW%2FK%2FQ59ZQgz77UZMFBCUEJfxjgSg09qY7iu1D%2FlxbzenPVc%2BapryD%2FcBYLJQQp%2FJoNuVOOpTXu17Q9YnadMMfO3G%2F9CZbO%2Fy2mZQJYYhF80FZE%2FORbmuS8T3OwvLzhZkX9IUmsyfySyMhIMLFF9MfSOl%2BPZAV4zCL6w9tw%2B6ChEnicFnywvuh2lhbgEdJZXHQ7SwkwXW03aXluQUd7eIlmTko0Rxp3G5%2BQ4hZVR9M%2B7cQsExhUyqWlccCrQk%2BaH3ywTr22YC5nwDkWgQnCLcYngFcTGisJbzON51AZRDJ709jqXVCBhTtvp4%2B5wTWVGr%2B1DKnxb0YDOywav8vHmUDFYPYddiw3PgK8YtmBXp9GEHCu5i%2FbsNnbUmZ6bk5Kbq5N9b%2FX2T%2Baf5t7LDdcNBrQv0zJaqS8l2ZF2vJ%2BERmUamCzOecPc8SnWZsIcIVjlax%2B4IlWJPNpoaaH9WhsixjbrjIBOjBcS5X1quUx95OSerQeaqBIrAj%2FahMiOhNclqPqTq%2FUHOTJGUf7Yr2Z267z1ch3FpmQ0T1B3HWNMqT1O18AbtdylV06osdrpn6Hlqu8RG6nwLOSoZOxXOX%2B4MtV1tiHRIihsNn7044rWlzV95LFg5p%2BOs6UFY2X8bF88RfBHDNziru5xqLKSiuQi%2BNdvqdLNQU9pSzT34JpNbu1VKU%2F9vyioJLEvVArr2SNUE5b4zdoRd%2FoRqyjjClaHV3O7r%2FnIHQJGV%2BrNe1i6IjjzGjXn6DqVlPFWq3ftkM9b0f10nZIfQ3bNX9ZkshvlVhN75wmkUgkEolEIpFIJBKJRCIRM5r5D9WH9bFOGhrhAAAAAElFTkSuQmCC&style=flat-square)](https://github.com/PlazmaMC/Thunderbolt/actions/workflows/upstream.yml)
[![Forks](https://img.shields.io/github/forks/PlazmaMC/Thunderbolt?label=forks&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9bxSIVh1YQcYhQnSyIiuimVShChVArtOpgcukXNGlIUlwcBdeCgx+LVQcXZ10dXAVB8APE0clJ0UVK/F9SaBHjwXE/3t173L0D/PUyU82OMUDVLCOViAuZ7KrQ9YogwujDEGYkZupzopiE5/i6h4+vdzGe5X3uz9Gj5EwG+ATiWaYbFvEG8dSmpXPeJ46woqQQnxOPGnRB4keuyy6/cS447OeZESOdmieOEAuFNpbbmBUNlXiSOKqoGuX7My4rnLc4q+Uqa96TvzCU01aWuU5zEAksYgkiBMioooQyLMRo1UgxkaL9uId/wPGL5JLJVQIjxwIqUCE5fvA/+N2tmZ8Yd5NCcaDzxbY/hoGuXaBRs+3vY9tunACBZ+BKa/krdWD6k/RaS4seAb3bwMV1S5P3gMsdoP9JlwzJkQI0/fk88H5G35QFwrdA95rbW3Mfpw9AmrpK3gAHh8BIgbLXPd4dbO/t3zPN/n4Ax9dyyerighsAAAAGYktHRAAAAAAAAPlDu38AAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfmCBMVNCYN3/YeAAAA/UlEQVQ4y7WTQUoDQRBFf01czlJcxUyOINGjjAvFHMFzZGdygOwDwTtk6UZcqLlAxCAuMigug89FamIzdAIN+qGhq/6v6qrqbumvAJwBj8AHMAQs4DJgBHy65jSW4Bl4AaZsUAbcufumrnmquSzIcSzpTtLA7XbA1fuBa9qxCob8YgUUAdcFqoC/iSXIgLELOhG+49w4nM+2BTP7ljR3M4/MufbNzYxdN1E0Sm2ialZnsVIllZKOJF24eyLpXdKtmS1S3sYMmO3THOwJziUdbrbkZvaVcnILeAh6vweylAQ9D7z2BXCS0sJS0lrSpdtrSW+pn6sPLIFX4Er/hR9C0wl1FTBzNwAAAABJRU5ErkJggg==&style=flat-square&color=green)](https://github.com/PlazmaMC/Thunderbolt/forks)
[![Watchers](https://img.shields.io/github/watchers/PlazmaMC/Thunderbolt?label=watchers&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9bxSIVh1YQcYhQnSyIiuimVShChVArtOpgcukXNGlIUlwcBdeCgx+LVQcXZ10dXAVB8APE0clJ0UVK/F9SaBHjwXE/3t173L0D/PUyU82OMUDVLCOViAuZ7KrQ9YogwujDEGYkZupzopiE5/i6h4+vdzGe5X3uz9Gj5EwG+ATiWaYbFvEG8dSmpXPeJ46woqQQnxOPGnRB4keuyy6/cS447OeZESOdmieOEAuFNpbbmBUNlXiSOKqoGuX7My4rnLc4q+Uqa96TvzCU01aWuU5zEAksYgkiBMioooQyLMRo1UgxkaL9uId/wPGL5JLJVQIjxwIqUCE5fvA/+N2tmZ8Yd5NCcaDzxbY/hoGuXaBRs+3vY9tunACBZ+BKa/krdWD6k/RaS4seAb3bwMV1S5P3gMsdoP9JlwzJkQI0/fk88H5G35QFwrdA95rbW3Mfpw9AmrpK3gAHh8BIgbLXPd4dbO/t3zPN/n4Ax9dyyerighsAAAAGYktHRAAAAAAAAPlDu38AAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfmCBMVNw4TRw0nAAAA3UlEQVQ4y83SP04CURAG8I0lewHOwAFUaiwkdmAlp8CL4FHopfIvtOIJWE3opIBK489mQPKy6xYWOskkL9/MN/PNzMuyf2fIcYkZVuGzwPI68gle8Yl7jMIfAntBp4o8wAeecFgSP8I8cgZp8DwC12j8oLCBCd7R34ItbHCzT8ZZSC7QTYrcYo1WhjGWaCbdCt+2SGLN4IwPfnu07QjrkhG6oWKB0+TMd7sRAuzHYuqWmO8tsVd1xjmOS8htPEfORVWHTmweHnEVPg2sqPxIicxhFFjhLd7D2q/8J/YFHSJt9VSqQ08AAAAASUVORK5CYII=&style=flat-square&color=green)](https://github.com/PlazmaMC/Thunderbolt/watchers)

</div>

[Plazma]: https://github.com/PlazmaMC/Plazma

## :warning: Warning

- Thunderbolt는 Plazma에 추가하기에는 아직 **너무 실험적인 패치를 테스트**하기 위한 하위 프로젝트 입니다.
- Plazma에 패치를 추가하기 전 먼저 실험하기 위한 공간으로, 공개 서버에서 **사용하기 매우 부적합**합니다.
  - Thunderbolt는 패치 시스템이 어떻게 작동하는지 잘 이해하고 있는 사용자만 사용하는것을 권장합니다.
  - Thunderbolt를 공개 서버에서 사용하는 것은 본인 재량이나, Thunderbolt를 사용함으로써 발생하는 **모든 문제에 대한 책임은 사용자에게** 있습니다.
- Thunderbolt에서는 매 업데이트마다 다음과 같은 문제가 발생할 수 있습니다.
  - 게임 플레이가 기존 바닐라와 크게 달라질 수 있음.
  - 기존 월드가 크게 손상될 수 있음.
  - 플러그인이 정상적으로 동작하지 않을 수 있음.
  - 서버가 예기치 못한 상황에 갑자기 충돌할 수 있음.
  - 기타 예기치 못한 부분에서 심각한 오류가 발생할 수 있음.
- **안정적인 구현체를 사용하고 싶다면 대신 [Plazma]를 사용하십시오.**

---

- Thunderbolt is a sub-project to test patches that are **still too experimental** to add to the Plazma.
- Space for experimentation before adding patches to Plazma, which is **highly unsuitable for use on public servers.**
  - Thunderbolt is recommended only for users who understand how patch systems work.
  - It is at your own discretion to use Thunderbolt on a public server, but **you are responsible for any problems caused by using Thunderbolt.**
- Thunderbolt can cause the following problems with each update.
  - Gameplay may be significantly different from traditional vanilla.
  - Existing worlds may be severely compromised.
  - The plug-in may not work properly.
  - Server may crash suddenly in unexpected situations.
  - Severe errors may occur in other unexpected areas.
- **If you want to use a stable implementation, use [Plazma] instead.**

---

## 💬 About Thunderbolt...

- Thunderbolt is a sub-project to test patches that are still too experimental to add to the Plazma.
- It is divided into several Flavor, and you can download the Flavor you want and experiment with it yourself.

## ❌ Thunderbolt's Restrictions

- Only the most up-to-date version available from Plazma is always supported (whether it's a stable version or not)
- Each Flavor is not guaranteed stability and can be deleted suddenly.

## 🎨 Flavors

- [Vanilla (Base Flavor)](https://github.com/PlazmaMC/Thunderbolt/release/release/base)
  - Implemented [Noisium](https://modrinth.com/mod/noisium)
- [feat/remove-timings](https://github.com/PlazmaMC/Thunderbolt/release/release/feat/remove-timings)
  - All features from base
  - Remove all timings related implementations except API access (Exception occurs when accessing API)
- [feat/sector-file](https://github.com/PlazmaMC/Thunderbolt/release/release/feat/sector-file)
  - All features from base
  - Implements Paper's new [Sector File](https://github.com/PaperMC/Paper/pull/10231)

## ⚖️ License

- This project and all patches are licensed under the [MIT license](LICENSE.md) unless otherwise noted in the patch headers.

## 🌀 Sponsorship - Minecraft Development Dictionary (KOREAN)

[![Minecraft Development Dictionary](https://img.shields.io/discord/911980670123905054?color=%239c91fd&label=MDD&logo=discord&style=for-the-badge&logoColor=ffffff)](https://discord.gg/AZwXTA9Pgx)
- 한글로 번역&정리된 Minecraft와 서드파티 버킷들의 소식들을 빠르게 만나볼 수 있습니다.
- Skript와 Plugin등 서버 개발과 관련된 질문에 대한 답변을 받으실 수 있습니다.
