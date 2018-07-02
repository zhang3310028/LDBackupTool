version <-"v0.1.1";

# max.size: accumulate max size <n T> 
# max.date: max date
# filt_str: filter strings
# return type : data.frame: cols:path,chip_name,size,date

data_list<-function(src_dir=".",max.size=NA,max.date=Sys.time(),filt_str=c()){

#----------prepare dat start
	file_lists<-lapply(src_dir,function(xx){
		list.dirs(xx,full.names=T,recursive=F)
	})
	files<-unlist(file_lists);

	result<-lapply(files,function(xx){
		list(
			"absdir"=xx
			,"basedir"=basename(xx)
			,"mtime"=file.info(xx)$mtime)
	})
	dat<-do.call(rbind,lapply(result,data.frame))
	dat<-dat[order(dat$mtime),,drop=F]
	sapply(filt_str,function(xx){
		dat<<-dat[!grepl(xx,dat$basedir,perl=T),]
	})
#----------prepare dat end

#---------- mtime filter start
	dat_filt<-dat[dat$mtime<as.POSIXct(max.date),,drop=F]
#---------- mtime filter end
#---------- acc_size filter start
  tmp.size <- 0
	
	dat_filt$acc_size<-sapply(1:nrow(dat_filt),function(xx){
		
		if(tmp.size == -1){
			tmp.size=-1
		}else{
			chip_dirsize<-dir_size(as.character(dat_filt[xx,]$absdir[[1]]))
			tmp.size <<- tmp.size + chip_dirsize
		}

		if(!is.na(max.size) && tmp.size > 1024*1024*1024*1024*max.size){
			tmp.size=-1
		}
		
		tmp.size
	})
	dat_filt<-dat_filt[dat_filt$acc_size != -1,,drop=F]
	
#---------- acc_size filter end
	
	dat_filt
}

dir_size<-function(dir_path=c(".")){
	total_size<-sapply(dir_path,function(xx){
		files<-list.files(xx,recursive=T)
		files
		info<-file.info(paste(xx,"/",files,sep=""))
		all_file_size<-info$size
		total_size <-Reduce("+",all_file_size);
		total_size
	})
	total_size[total_size%in%NA] <- 0
	total_size
}

# path_list: a vector
# dest_dir: string
copy_files<-function(dest_dir=".",path_list=c("")){
	tt<-sapply(path_list,function(xx){
		value<-file.copy(from=xx,to=dest_dir,recursive=T);
		if(value){
			unlink(xx,recursive = TRUE)
		}
	})
	
}

# path_list: a vector
mk_cp_cmd<-function(dest_dir=".",path_list=c("")){
	cmd<-sapply(path_list,function(xx){
		paste("cp -r ",xx," ",dest_dir,sep="")
	})
}


mk_backup_cmd<-function(dest_dirs=".",path_list=c("")){
	cmd<-sapply(path_list,function(xx){
		cmd_result<-""
		
		cmd_list<-sapply(dest_dirs,function(yy){
			paste("cp -u -v -r ",xx," ",yy,sep="")
		})
		
		cmd_result<-paste(cmd_list,collapse=" && ")
		rst<-paste(cmd_result," && rm -r ",xx,sep="")
		rst
	})
	
	cmd
}

write_cmd_to_file<-function(cmds=c(),filename=""){
	cat("date",sep="\n",file=filename)
	cat(cmds,sep="\n",append=T,file=filename)
	cat("date",sep="\n",append=T,file=filename)
}
