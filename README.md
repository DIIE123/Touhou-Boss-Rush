# Touhou Boss Rush
A replica of Touhou made for my ISU project for ICS4U. All credits for art and inspiration go to Touhou Project, and its creator, ZUN.
![image](https://github.com/user-attachments/assets/e5aa1d4e-4351-4bfc-bdc1-713798caccba)

## Requirements
- [Java](https://www.java.com/en/download/ "Java Download") 16 or higher, including:
  - Java SE 16+
  - Java JRE 1.8.0+
<br>
To play the game, run the file "Touhou Boss Rush.jar" inside the main folder.

## How to Play
### Gameplay
The main objective of the game is to shoot the enemy and gain score while dodging the enemy bullets. The goal is to survive to the end and to collect as much score as possible to be at the top of the leaderboard.

#### Healthbar
At the top of the screen,  you can see the enemy health bar, the enemy lives, the time remaining, and the active spell card. If the enemy health bar is currently green, then it is using an normal attack. If it is currently red, then it is using a spell card, a special attack that is harder to dodge. Capturing a spell card will give you a boost in score.

#### Scoreboard
On the right side of the screen, you can see your current game score, the number of lives you have, and the number of bombs you have. You start each stage with 4 lives and 4 bombs in total. If you are hit by a bullet, you lose a life. You can instead press [X] to use a bomb to clear the screen of bullets and unleash a powerful attack to damage the boss. You can also press [Shift] to focus, which allows you to see your hitbox, move slower, and narrow your attacks.

### Characters
There are 3 characters to choose from:
| Name | Reimu Hakurei              | Marisa Kirisame         | Sanae Kochiya |
|----------- | -----------                | -----------             | ----------- |
| Portrait | ![Touhou Reimu Portrait](https://github.com/user-attachments/assets/164e7128-95cd-455a-bd2e-4a147c6b2295) | ![Touhou Marisa Portrait](https://github.com/user-attachments/assets/f4818384-7051-4f08-8ae6-531892c98f50) | ![Touhou Sanae Portrait](https://github.com/user-attachments/assets/2856435f-1705-4c54-ac68-5bf6c1270268) |
| Shot Type | Homing | Straight | Spread |
| Bomb | Fantasy Seal | Master Spark | Stardust Reverie |
| Speed | Slow | Fast | Medium |

### Scoring
| Game Action                | Amount                                                             |
| -----------                | -----------                                                        |
| Capturing a Spellcard      | Starts at 2,000,000 and decreases as time passes                   |
| Grazing a Bullet           | 2,000 per bullet                                                   |
| Hitting the Enemy          | 10-15 per bullet hit, varies by character                          |

| Stage Clear                | Amount                                                             |
| -----------                | -----------                                                        |
| Lives Remaining            | 1,000,000 per life                                                 |
| Bombs Remaining            | 200,000 per bomb                                                   |
| Stage Difficulty           | 2,000,000 * Stage Multiplier (1 for easy, 2 for medium, 3 for hard)|

### Game Controls
| Keybind      | Description          |
| -----------  | -----------          |
| W            | Move Up              |
| A            | Move Left            |
| S            | Move Down            |
| D            | Move Right           |
| Z            | Shoot                |
| X            | Bomb                 |
| Shift        | Focus                |

### Debug Controls
| Keybind      | Description          |
| -----------  | -----------          |
| H            | Toggle Hitboxes      |
| I            | Toggle Invincibility |

## Credits
- Dylan Wang - Players, Enemies, Enemy Attacks, Bullets, Helped make Menu <br>
- Simon Gao - Menu, Score, Game Balancing, Helped make Players <br>
- ZUN - Art, Music, Inspiration
