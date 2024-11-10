Responsibilities:
Simon: Menu, Score, Game Balancing, Helped make Players
Dylan: Players, Enemies, Enemy Attacks, Bullets, Helped make Menu

Missing functions:
None

Added functions:
- Text File Streaming
- Sound
- Cooler Attacks
   - The attacks are way more complicated than originally planned

Debug buttons:
- Press H to show hitboxes of bullets
- Press I to become invincible
    - Near miss is turned off when invincible because it activates way too much

Known bugs:
- Occasionally, a bullet doesn't spawn or is added to the bulletList as null
- Sometimes the images don't load for a few seconds when entering a level
- Bullet sounds still play when player bombs
- Cannot press x to go back when entering name
- Enter name field expands when invalid name is entered

Important info:
- Make sure to run Menu class to start the game (endMenu has a main that doesn't run anything)
- Game might be very laggy if your computer is bad
- You need to use mouse to click "start" after entering name because your keyboard is in the text field
- Score will overwrite name if it is the same (intentional)