# No More Pane

## About

Have you ever been annoyed by the glitchiness of glass panes in the Crystal Hollows of Hypixel Skyblock? Have you experienced inventory glitches, such as items popping back into your cursor or dropping out of your inventory? If so, this mod is for you.

## How It Works

- When the client receives a 'block broken' packet from the server, it immediately updates the glass panes around the block to align with what the server believes is there. The reason that you fall through the panes is that the server never sends the updated information to the client.
- When you move an item in your inventory that has a large amount of data, but drag your cursor slightly as you put it down, the server doesn't like that and puts it back in your cursor or drops it. We fix this by modifying the click packet for a one-item stack to replace the 'drag' tag with a simple 'click' tag.

---

## License

This project is licensed under the terms of the GNU General Public License, Version 2.0. See the LICENSE file for details.
