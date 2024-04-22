package com.example.smarteyes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.smarteyes.mapper")
public class SmartEyesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartEyesApplication.class, args);
        System.out.println("                                                                                                        \n" +
                "                                                                                                        \n" +
                "                                             ,--.'|_                                                    \n" +
                "                 ,---,              __  ,-.  |  | :,'     ,---,.                                        \n" +
                "  .--.--.    ,-+-. /  |           ,' ,'/ /|  :  : ' :   ,'  .' |                             .--.--.    \n" +
                " /  /    '  ,--.'|'   |  ,--.--.  '  | |' |.;__,'  /  ,---.'   , ,---.       .--,   ,---.   /  /    '   \n" +
                "|  :  /`./ |   |  ,\"' | /       \\ |  |   ,'|  |   |   |   |    |/     \\    /_ ./|  /     \\ |  :  /`./   \n" +
                "|  :  ;_   |   | /  | |.--.  .-. |'  :  /  :__,'| :   :   :  .'/    /  |, ' , ' : /    /  ||  :  ;_     \n" +
                " \\  \\    `.|   | |  | | \\__\\/: . .|  | '     '  : |__ :   |.' .    ' / /___/ \\: |.    ' / | \\  \\    `.  \n" +
                "  `----.   \\   | |  |/  ,\" .--.; |;  : |     |  | '.'|`---'   '   ;   /|.  \\  ' |'   ;   /|  `----.   \\ \n" +
                " /  /`--'  /   | |--'  /  /  ,.  ||  , ;     ;  :    ;        '   |  / | \\  ;   :'   |  / | /  /`--'  / \n" +
                "'--'.     /|   |/     ;  :   .'   \\---'      |  ,   /         |   :    |  \\  \\  ;|   :    |'--'.     /  \n" +
                "  `--'---' '---'      |  ,     .-./           ---`-'           \\   \\  /    :  \\  \\\\   \\  /   `--'---'   \n" +
                "                       `--`---'                                 `----'      \\  ' ; `----'               \n" +
                "                                                                             `--`                       ");
    }
}
