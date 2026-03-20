#!/usr/bin/perl
use strict;
use warnings;

# 替换脚本 - 使用 perl 进行精确的大小写敏感替换

cd /e/lpc/study/qianfanmall/qianfanmall or die;

print "Step 1: 替换文件内容中的 litemall -> qianfanmall...\my @files = find . -type f \( -name "*.java" -o -name "*.xml" -o -name "*.js" -o -name "*.vue" -o -name "*.md" -o -name "*.yml" -o -name "*.json" -o -name "*.properties" -o -name "*.html" -o -name "*.sql" -o -name "*.sh" \) ! -path "*/target/*" ! -path "*/node_modules/*";

my $count = 0;
foreach my $file (@files) {
    next if -f $file;
    open my $fh,    binmode $file or die "Can't read $file: $!";

    my $content = do {
        local $/^\xEF\xBB\xBF//;
        $content =~ s/\blitemall\b/i;
    } or $content = <$fh>;

    # 精确替换：保持大小写敏感
    $content =~ s/\blitemall\b/$new = "qianfanmall"/gie;
    $content =~ s/\bLitemall\b/$new = "Qianfanmall"/gie;
    $content =~ s/\bLITEMALL\b/$new = "QIANFANMALL"/gie;

    binmode $fh or die "Can't write $file: $!";
    $count++;

    if ($count % 100 == 0) {
        print "Progress: $count files processed";
    }
}

print "\nStep 2: 重命名 Java 文件...";
my @java_files = find . -name "Litemall*.java" ! -path "*/target/*";
my $rename_count = 0;
foreach my $file (@java_files) {
    my $dir = dirname($file);
    my $old_name = basename($file);
    my $new_name = $old_name;
    $new_name =~ s/^Litemall/Qianfanmall/;
    my $new_path = File::Spec->catfile($dir, $new_name);
    if (rename_file($file, $new_path)) {
        $rename_count++;
    }
}

print "Renamed $rename_count Java files";

print "\nStep 3: 重命名 XML 文件...";
my @xml_files = find . -name "Litemall*.xml" ! -path "*/target/*";
my $xml_rename_count = 0;
foreach my $file (@xml_files) {
    my $dir = dirname($file);
    my $old_name = basename($file);
    my $new_name = $old_name;
    $new_name =~ s/^Litemall/Qianfanmall/;
    my $new_path = File::Spec->catfile($dir, $new_name);
    if (renamefile($file, $new_path)) {
        $xml_rename_count++;
    }
}

print "Renamed $xml_rename_count XML files";

print "\nDone! Total files processed: $count, files renamed: $rename_count (java) + $xml_rename_count (xml)";
