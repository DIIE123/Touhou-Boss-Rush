# Touhou Boss Rush
A replica of Touhou made for my ISU project for ICS4U.<br><br>
![image](https://github.com/user-attachments/assets/e5aa1d4e-4351-4bfc-bdc1-713798caccba)

## Requirements
- [Java](https://www.java.com/en/download/ "Java Download") 16 or higher, including:
  - Java SE 16+
  - Java JRE 1.8.0+
<br>
To play the game, run the file "Touhou Boss Rush.jar" inside the main folder.

## How to Play
### Gameplay
The main objective of the game is to shoot the enemy and gain score while dodging the enemy's bullets. The goal is to survive to the end while collecting as much score as possible.

#### Healthbar
At the top of the screen,  you can see the enemy's health bar, the enemy's number of lives, the time remaining for the attack, and the active spell card. If the enemy health bar is green, then it is using an normal attack. If it is red, then it is using a spell card, a special attack that is harder to dodge. Capturing a spell card will give you a boost in score.<br><br>
![image](https://github.com/user-attachments/assets/4a47fd35-6f14-4352-be6e-58e7fd3c953f)

#### Scoreboard
On the right side of the screen, you can see your current game score, the number of lives you have, and the number of bombs you have. You start each stage with 4 lives and 4 bombs in total. If you are hit by a bullet, you lose a life. You can instead press [X] to use a bomb to clear the screen of bullets and unleash a powerful attack to damage the boss. You can also press [Shift] to focus, which allows you to see your hitbox, move slower, and narrow your attacks.<br><br>
![image](https://github.com/user-attachments/assets/100d8de0-9e2e-479a-97dd-9f0c9dc12af9)
![image](https://github.com/user-attachments/assets/4d7d1eb7-1e1b-4083-afcc-af4584850431)
![image](https://github.com/user-attachments/assets/71644ae0-3d4b-46b9-809f-6c06db4bf83a)

## Characters
There are 3 characters to choose from:
| Name | Reimu Hakurei | Marisa Kirisame | Sanae Kochiya |
|----------- | ----------- | ----------- | ----------- |
| Portrait | ![Touhou Reimu Portrait](https://github.com/user-attachments/assets/164e7128-95cd-455a-bd2e-4a147c6b2295) | ![Touhou Marisa Portrait](https://github.com/user-attachments/assets/f4818384-7051-4f08-8ae6-531892c98f50) | ![Touhou Sanae Portrait](https://github.com/user-attachments/assets/2856435f-1705-4c54-ac68-5bf6c1270268) |
| Description | The Hakurei shrine maiden. | The magician of the forest. | The servant of the Moriya gods. |
| Shot Type | Homing | Straight | Spread |
| Bomb | Fantasy Seal | Master Spark | Stardust Reverie |
| Speed | Slow | Fast | Medium |

## Enemies
There are 3 bosses, each with their own unique attacks:
| Name | Cirno | Sakuya | Yukari |
|----------- | ----------- | ----------- | ----------- |
| Portrait | ![Touhou Cirno Portrait](https://github.com/user-attachments/assets/512fb640-6672-456b-b420-b4bbe109e099) | ![Touhou Sakuya Portrait](https://github.com/user-attachments/assets/5de19f88-effb-4fe0-be0c-5d74f5096402) | ![Touhou Yukari Portrait](https://github.com/user-attachments/assets/dc3f5839-5030-4622-ac15-5598580f4e55) |
| Description | The infamous ice fairy, ready to prove she's the strongest. | The maid of the Scarlet Devil Mansion, here to stop you from intruding. | The Youkai of Boundaries, annoyed that you disturbed her sleep. |
| Difficulty | Easy | Medium | Hard |
| Lives | 2 | 3 | 3 |

## Scoring
There are multiple ways to gain score within a stage:
| Game Action                | Amount                                                             |
| -----------                | -----------                                                        |
| Capturing a Spellcard      | Starts at 2,000,000 and decreases as time passes                   |
| Grazing a Bullet           | 2,000 per bullet                                                   |
| Hitting the Enemy          | 10-15 per bullet hit, varies by character                          |

After clearing a stage, your score will be increased by the following amounts:
| Stage Clear                | Amount                                                             |
| -----------                | -----------                                                        |
| Lives Remaining            | 1,000,000 per life                                                 |
| Bombs Remaining            | 200,000 per bomb                                                   |
| Stage Difficulty           | 2,000,000 * Stage Multiplier (1 for easy, 2 for medium, 3 for hard)|

## Controls
### Game Controls
| Keybind      | Description          |
| -----------  | -----------          |
| Up Arrow     | Move Up              |
| Left Arrow   | Move Left            |
| Down Arrow   | Move Down            |
| Right Arrow  | Move Right           |
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
