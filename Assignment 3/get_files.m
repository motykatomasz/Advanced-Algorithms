function file_names = get_files(path_to_dir)

files = dir(path_to_dir);
notDirFlags = ~[files.isdir];
files = files(notDirFlags);
file_names = strings(1,length(files))
for i = 1: length(files)
    file = files(i);
    file_names(1,i) = file.name;
end



