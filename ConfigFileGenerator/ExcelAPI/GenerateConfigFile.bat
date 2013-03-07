set File_Generator_Home=C:\Users\iggo\Desktop\ConfigFileGeneratorMaterial\ConfigFileGenerator
set Excel_File_Location=C:\Users\iggo\Desktop\ConfigFileGeneratorMaterial\SeverityConfigurationFile.xlsm
set Config_File_Location=C:\Users\iggo\Desktop\ConfigFileGeneratorMaterial\GeneratedConfigFile.xml
cd %File_Generator_Home%
java -jar ConfigFileGenerator.jar %Excel_File_Location% %Config_File_Location%