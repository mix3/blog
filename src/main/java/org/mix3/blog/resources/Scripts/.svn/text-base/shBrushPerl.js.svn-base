/* Perl 5 syntax contributed by Fayland Lam */
dp.sh.Brushes.Perl = function()
{
	var keywords = 'continue foreach require package scalar format unless local until while elsif next last goto else redo sub for use no if my our';
	
	var builtins = 'getprotobynumber getprotobyname getservbyname gethostbyaddr gethostbyname getservbyport getnetbyaddr getnetbyname getsockname getpeername setpriority getprotoent setprotoent getpriority endprotoent getservent setservent endservent sethostent socketpair getsockopt gethostent endhostent setsockopt setnetent quotemeta localtime prototype getnetent endnetent rewinddir wantarray getpwuid closedir getlogin readlink endgrent getgrgid getgrnam shmwrite shutdown readline endpwent setgrent readpipe formline truncate dbmclose syswrite setpwent getpwnam getgrent getpwent ucfirst sysread setpgrp shmread sysseek sysopen telldir defined opendir connect lcfirst getppid binmode syscall sprintf getpgrp readdir seekdir waitpid reverse unshift symlink dbmopen semget msgrcv rename listen chroot msgsnd shmctl accept unpack exists fileno shmget system unlink printf gmtime msgctl semctl values rindex substr splice length msgget select socket return caller delete alarm ioctl index undef lstat times srand chown fcntl close write umask rmdir study sleep chomp untie print utime mkdir atan2 split crypt flock chmod BEGIN bless chdir semop shift reset link stat chop grep fork dump join open tell pipe exit glob warn each bind sort pack eval push keys getc kill seek sqrt send wait rand tied read time exec recv eof chr int ord exp pos pop sin log abs oct hex tie cos vec END ref map die __DATA__ __END__ __PACKAGE__ qw qq qr lc do \@_ \$_';
	//-C -b -S -u -t -p -l -d -f -g -s -z uc -k -e -O -T -B -M -A -X -W -c -R -o -x  -w -r
	
	var ops = 'xor and not cmp or ne eq ge le gt lt \->';
	// ||= ... .= x= %= /= *= -= += =~ ** -- .. ||  ++ ^= |=  ,  : ? ^ | x % / * < & \ ~ !  . - +  <<= <=> &&= => !~ &= != >= <= >> << = > -> = \&\& ==
	
	var commonlibs = 'strict warnings Carp lib vars base blib constant version';

	this.regexList = [
		{ regex: new RegExp('#.*$', 'gm'),								css: 'comment' },			// comments
		{ regex: new RegExp('"""(.|\n)*?"""', 'g'),						css: 'string' },			// multi-line strings "
		{ regex: new RegExp('\'\'\'(.|\n)*?\'\'\'', 'g'),				css: 'string' },			// multi-line strings '
		{ regex: new RegExp('"(?:\\.|[^\\""])*"', 'g'),					css: 'string' },			// strings "
		{ regex: new RegExp('\'(?:\\.|[^\\\'\'])*\'', 'g'),				css: 'string' },			// strings '
		{ regex: new RegExp(this.GetKeywords(keywords), 'gm'),			css: 'keyword' },			// keywords
		{ regex: new RegExp(this.GetKeywords(ops), 'gm'),			    css: 'op' },			    // ops
		{ regex: new RegExp(this.GetKeywords(builtins), 'gm'),			css: 'builtins' },			// builtin objects, functions, methods, magic attributes
		{ regex: new RegExp(this.GetKeywords(commonlibs), 'gm'),		css: 'commonlibs' }			// common standard library modules
		];

	this.CssClass = 'dp-pl';
}

dp.sh.Brushes.Perl.prototype	= new dp.sh.Highlighter();
dp.sh.Brushes.Perl.Aliases	= ['pl', 'perl'];
