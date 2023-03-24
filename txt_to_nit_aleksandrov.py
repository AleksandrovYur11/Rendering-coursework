
#import os
input_file_name = 'C:\\ITMO\\1 курс\\Основы компьютерной графии\\Course_Rendering\\Rendering_Aleksandrov\\render.txt'
output_file_name = 'renderAleksandrov.radiance'

def process_table(text_file, out):
    line = text_file.readline()
    while line != '\n' and line != '':
        tokens = line.split()
        out.append(list(map(float, tokens)))
        line = text_file.readline()

data = []
wl_400 = []
wl_500 = []
wl_600 = []
wl_700 = []

with open(input_file_name) as text_file:
    line = text_file.readline()
    while line != '':
        tokens = line.split()
        if len(tokens) == 0:
            line = text_file.readline()
            continue
        if tokens[0] == 'wavelength' and tokens[1] == '400.0':
            process_table(text_file, wl_400)
        print("400!!!")
        if tokens[0] == 'wavelength' and tokens[1] == '500.0':
            process_table(text_file, wl_500)
        print("500!!!")
        if tokens[0] == 'wavelength' and tokens[1] == '600.0':
            process_table(text_file, wl_600)
        print("600!!!")
        if tokens[0] == 'wavelength' and tokens[1] == '700.0':
            process_table(text_file, wl_700)
        print("700!!!")
        line = text_file.readline()

data.append(wl_400)
data.append(wl_500)
data.append(wl_600)
data.append(wl_700)

#os.system("pause")
pp = PostProcessor(PPDataUnits.RADIANCE, [400,500,600,700], *data)
pp.flux_quantity_type = RADIOMETRIC

pp.SaveToHDR(output_file_name, overwrite = OverwriteMode.OVERWRITE)
