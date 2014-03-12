SimpleExplosionHeal
========

This is a Craftbukkit plugin. A much simpler version of CreeperHeal.

Usage
========

* Create some explosions.
* ???
* PROFIT!!!

Commands
========
* **/seh reload** - Reloads the plugin.

Permissions
========

    SimpleExplosionHeal.*:
        description: Gives access to all SimpleExplosionHeal commands.
        default: op
    SimpleExplosionHeal.admin:
        description: Gives access to all SimpleExplosionHeal administrative commands.
        children:
            SimpleExplosionHeal.reload: true
        default: op
    SimpleExplosionHeal.reload:
        description: Reloads the plugin.
        default: op
