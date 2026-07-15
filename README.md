Create: Aquatic Ambitions
----------------------------------------------------------------------------------------------------------------
A Create add-on centered on the **Conduit Cage**: a block that lets you automate prismarine and copper without
guardian farms, age your copper faster, and revive/farm dead coral, duplicate sponges and much more!

Pump a fluid into the Conduit Cage's bottom face to awaken it. Once awake it does two things:

- **Fan processing (Channeling):** air blown through an awakened cage becomes a Channeling stream: run items
  through it to age/oxidize copper, revive dead coral, and process prismarine. It's a compact stand-in for a full
  prismarine conduit ring. 
- **Effects on nearby entities:** the cage applies effects to entities in range based on the fluid inside it:
  water grants Conduit Power, other fluids grant other effects (fully customizable, see below). A mode slot on the
  block picks which entities are affected.

Made by DaviO
-----------------------------------------------------------------------------------------------------------------
This mod requires Create 6.0.7 or newer to work correctly
JEI is recommended but not required! 

-----------------------------------------------------------------------------------------------------------------

## Customizing Conduit cage effects (datapacks)

Conduit cage effects are now data-driven (yay!). Add, disable, or remap them with a datapack, no add-on jar needed.
Each effect matches one JSON at `data/<your_pack>/create_aquatic_ambitions/conduit_effect/<name>.json`. Fields: `color` (`#RRGGBB`), `lang_key`,
`fluid_tag` (fluids that trigger it), and optional `mob_effect`, `behavior` (`apply`/`clear_effects`/`set_on_fire`),
and `enabled`. Reload the world afterward or restart the server. `/reload` doesn't rebuild datapack registries.

**Add an effect example**: Ars Nouveau Mana Regeneration from Create's Chocolate:

`data/my_pack/create_aquatic_ambitions/conduit_effect/mana_regen.json`
```json
{
  "color": "#7B4DFF",
  "lang_key": "effect.ars_nouveau.mana_regen",
  "mob_effect": "ars_nouveau:mana_regen",
  "fluid_tag": "my_pack:conduit_effects/gives_mana_regen"
}
```
`data/my_pack/tags/fluid/conduit_effects/gives_mana_regen.json` (tags use the normal single-namespace path)
```json
{ "values": ["create:chocolate"] }
```

Once an effect (vanilla or modded) is registered here, channeling a **potion** of that effect works automatically. The cage reads the potion's contents and amplifier. The `fluid_tag` is only for triggering the effect from a
non-potion fluid; point it at an empty/unused tag if you only care about potions.

**Disable a built-in effect example** — copy the mod's own file and add `"enabled": false`, e.g. to stop poison and lava/fire:

`data/create_aquatic_ambitions/create_aquatic_ambitions/conduit_effect/poison.json`
```json
{
  "color": "#4E9331",
  "lang_key": "effect.minecraft.poison",
  "mob_effect": "minecraft:poison",
  "fluid_tag": "create_aquatic_ambitions:conduit_effects/gives_poison",
  "enabled": false
}
```
Same idea for `burning.json` (and any other built-in id: `speed`, `wither`, `haste`, `clear`, …). 
