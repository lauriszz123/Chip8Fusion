# Chip8Fusion

## Description

A Chip-8 emulator with an assembler and a high-level language all written in Java.

## Support

Chip8Fusion supports all Chip-8 Games and Programs.
The extended instructions don't overlap with the old instructions.
So all the games/programs can be written in a high level language and assembler.

## Assembler

Assembly language reference (also emulator reference): [Cowgod's Chip-8  Technical Reference v1.0](http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#Fx55)

New instruction additions consist of:

**8xy8 - CMB I, Vx, Vy** \
Set register I to Vx & Vy, where Vx = high byte, Vy = low byte.

**8xy9 - CMB Vx, Vy, I** \
Set register Vx & Vy to I, where Vx = high byte, Vy = low byte.

**F000 - JP I** \
Jump to location at register I.

**F001 - CALL I** \
Call subroutine at register I.

**Fx70 - LD BNK, Vx** \
Set memory bank to Vx.

**Fx71 - LD Vx, BNK** \
Set Vx to current memory bank.

## High-Level language: TL8 (or something else)

## Memory

First, the memory was extended from 4 KB to 64 KB.

This interpreter supports memory banking. Bank size is 8 KB
and start at 0xE000 to 0xFFFF. Bank size is about 2 MB so all sort
of applications can be created (Kernels, games, OS).

## Screen

Screen was extended from **64x32** pixels monochrome to **240x128** pixels colored.

### Color encoding:

A byte is split into four 2 bit values for brightness, red, green, blue.\
A black color is any Red, Green or  (or all at once)
value but the brightness is set to 0.\
A transparent color is just 0.

Example (Read the byte from HIGH -> LOW):

| 11         | 11  | 11    | 11   |
|------------|-----|-------|------|
| Brightness | Red | Green | Blue |

Drawing is only in sprites, so the sprites are byte based and not bit based.

Example:
```asm
LD V2, 0xF0
CMB I, V2, V3
DRW V0, V1, H

mario8x8:

```

### Memory Map:

0x0000 - 0x01FF is reserved for the interpreter.

0x0200 - 0xDFFF Available RAM for use.

0xE000 - 0xFFFF is reserved for memory banking.