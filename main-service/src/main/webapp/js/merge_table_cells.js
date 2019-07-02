;
(function($) {
	//$.fn就是$.prototype, 只是为了兼容早期版本的插件
	// 才保留了jQuery.prototype这个形式
	$.fn.mergeCell = function(options) {
		return this.each(function() {
			var cols = options.cols;
			for (var i = cols.length - 1; cols[i] != undefined; i--) {

				// console.debug(cols[i]);
				mergeCell($(this), cols[i]);
			}
			dispose($(this));
		});
	};

	function mergeCell($table, colIndex) {
		$table.data('col-content', ''); // 存放单元格内容
		$table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
		$table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的),
									// 默认一个"空"的jquery对象
		$table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数,
															// 用于最后一行做特殊处理时进行判断之用
		// 我们对每一行数据进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
		$('tbody tr', $table).each( function(index) {
			
			if(index == 0) {
				return true;
			}
			// td:eq中的colIndex即列索引
			var $td = $('td:eq(' + colIndex + ')', this);
			if(colIndex != 1) {//如果不是第二列stationName
				$td = $('td:eq(1)', this);
				$td1 =  $('td:eq(' + colIndex + ')', this);
			}
			//alert($td + "," + index);
			// 取出单元格的当前内容
			var currentContent = $td.html();
			// 第一次时走此分支
			if ($table.data('col-content') == '') {
				$table.data('col-content', currentContent);
				if(colIndex != 1) {//如果不是第二列stationName
					$table.data('col-td', $td1);
				} else {
					$table.data('col-td', $td);
				}
			} else {
				// 上一行与当前行内容相同
				if ($table.data('col-content') == currentContent) {
					// 上一行与当前行内容相同则col-rowspan累加, 保存新值
					var rowspan = $table.data('col-rowspan') + 1;
					$table.data('col-rowspan', rowspan);
					// 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
					if(colIndex != 1) {
						$td1.hide();
					} else {
						$td.hide();
					}
					
					// 最后一行的情况比较特殊一点
					// 比如最后2行 td中的内容是一样的,
					// 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
					if (++index == $table.data('trNum'))
						$table.data('col-td').attr('rowspan',$table.data('col-rowspan'));
				} else { // 上一行与当前行内容不同
				// col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
					if ($table.data('col-rowspan') != 1) {
						
						$table.data('col-td').attr('rowspan',
								$table.data('col-rowspan'));
					}
					// 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
					if(colIndex != 1) {//如果不是第二列stationName
						$table.data('col-td', $td1);
					} else {
						$table.data('col-td', $td);
					}
					$table.data('col-content', $td.html());
					$table.data('col-rowspan', 1); 	
				}
			}
		});
	}
	// 同样是个private函数 清理内存之用 
	function dispose($table) {
		$table.removeData();
	}
})(jQuery);