Demo: http://pakhomov.net.ru/caesar/ 

### Задача: зашифровать переданную строку шифром Цезаря.

У программы должно быть 2 режима:
1. Шифрование / расшифровка. Программа должна зашифровывать и расшифровывать текст, используя заданный криптографический ключ. 
Программа должна получать путь к текстовому файлу с исходным текстом и на его основе создавать файл с зашифрованным текстом.
2. Криптоанализ методом brute force 
Программа должна взламывать зашифрованный текст, переданный в виде текстового файла. 
Если пользователь выбирает brute force (брутфорс, поиск грубой силой), программа должна самостоятельно, путем перебора, подобрать ключ и расшифровать текст.

Изначально программа писалась для запуска в консоли. А потом была переписана на Java EE, для запуска на Apache Tomcat Server

