import os
import shutil

f = open("uebersetzungen.csv")
prop = {}
lang = []
for line in f:
    if line == '\n':
        continue
    command = line.strip().split(";")
    if(command[0] == "propertyname"):
        lang = command[1:]
        continue
    prop[command[0]] = command[1:]

os.chdir("src/main/resources")
open("messages.properties","x")
for spr in lang:
    f = open(f"messages_{spr}.properties","x")
    with open(f"messages_{spr}.properties", "w") as f:
        for property in prop:
            thing = property + "=" + prop[property][lang.index(spr)] + "\n"
            f.write(thing)
    if(os.stat("messages.properties").st_size == 0):
        shutil.copyfile(f"messages_{spr}.properties",'messages.properties')