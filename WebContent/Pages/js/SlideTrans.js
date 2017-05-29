try {

	var getID = function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};

	var Extend = function(destination, source) {
		for (var property in source) {
			destination[property] = source[property];
		}
		return destination;
	}

	var CurrentStyle = function(element) {
		return element.currentStyle
				|| document.defaultView.getComputedStyle(element, null);
	}

	var Bind = function(object, fun) {
		var args = Array.prototype.slice.call(arguments).slice(2);
		return function() {
			return fun.apply(object, args.concat(Array.prototype.slice
							.call(arguments)));
		}
	}

	var forEach = function(array, callback, thisObject) {
		if (array.forEach) {
			array.forEach(callback, thisObject);
		} else {
			for (var i = 0, len = array.length; i < len; i++) {
				callback.call(thisObject, array[i], i, array);
			}
		}
	}

	var Tween = {
		Quart : {
			easeOut : function(t, b, c, d) {
				return -c * ((t = t / d - 1) * t * t * t - 1) + b;
			}
		},
		Back : {
			easeOut : function(t, b, c, d, s) {
				if (s == undefined)
					s = 1.70158;
				return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
			}
		},
		Bounce : {
			easeOut : function(t, b, c, d) {
				if ((t /= d) < (1 / 2.75)) {
					return c * (7.5625 * t * t) + b;
				} else if (t < (2 / 2.75)) {
					return c * (7.5625 * (t -= (1.5 / 2.75)) * t + .75) + b;
				} else if (t < (2.5 / 2.75)) {
					return c * (7.5625 * (t -= (2.25 / 2.75)) * t + .9375) + b;
				} else {
					return c * (7.5625 * (t -= (2.625 / 2.75)) * t + .984375)
							+ b;
				}
			}
		}
	}

	// ��������,��������,�л�����
	var SlideTrans = function(container, slider, count, options) {
		this._slider = getID(slider);
		this._container = getID(container); // ��������
		this._timer = null; // ��ʱ��
		this._count = Math.abs(count); // �л�����
		this._target = 0; // Ŀ��ֵ
		this._t = this._b = this._c = 0; // tween����

		this.Index = 0; // ��ǰ����

		this.SetOptions(options);

		this.Auto = !!this.options.Auto;
		this.Duration = Math.abs(this.options.Duration);
		this.Time = Math.abs(this.options.Time);
		this.Pause = Math.abs(this.options.Pause);
		this.Tween = this.options.Tween;
		this.onStart = this.options.onStart;
		this.onFinish = this.options.onFinish;

		var bVertical = !!this.options.Vertical;
		this._css = bVertical ? "top" : "left"; // ����

		// ��ʽ����
		var p = CurrentStyle(this._container).position;
		p == "relative" || p == "absolute"
				|| (this._container.style.position = "relative");
		this._container.style.overflow = "hidden";
		this._slider.style.position = "absolute";

		this.Change = this.options.Change
				? this.options.Change
				: this._slider[bVertical ? "offsetHeight" : "offsetWidth"]
						/ this._count;
	};
	SlideTrans.prototype = {
		// ����Ĭ������
		SetOptions : function(options) {
			this.options = {// Ĭ��ֵ
				Vertical : true, // �Ƿ�ֱ���򣨷����ܸģ�
				Auto : true, // �Ƿ��Զ�
				Change : 0, // �ı���
				Duration : 0, // ��������ʱ��
				Time : 0, // ������ʱ
				Pause : 5000, // ͣ��ʱ��(AutoΪtrueʱ��Ч)
				onStart : function() {
				}, // ��ʼת��ʱִ��
				onFinish : function() {
				}, // ���ת��ʱִ��
				Tween : Tween.Quart.easeOut
				// tween����
			};
			Extend(this.options, options || {});
		},
		// ��ʼ�л�
		Run : function(index) {
			// ����index
			index == undefined && (index = this.Index);
			index < 0 && (index = this._count - 1) || index >= this._count
					&& (index = 0);
			// ���ò���
			this._target = -Math.abs(this.Change) * (this.Index = index);
			this._t = 0;
			this._b = parseInt(CurrentStyle(this._slider)[this.options.Vertical
					? "top"
					: "left"]);
			this._c = this._target - this._b;

			this.onStart();
			this.Move();
		},
		// �ƶ�
		Move : function() {
			clearTimeout(this._timer);
			// δ����Ŀ������ƶ����������һ�λ���
			if (this._c && this._t < this.Duration) {
				this.MoveTo(Math.round(this.Tween(this._t++, this._b, this._c,
						this.Duration)));
				this._timer = setTimeout(Bind(this, this.Move), this.Time);
			} else {
				this.MoveTo(this._target);
				this.Auto
						&& (this._timer = setTimeout(Bind(this, this.Next),
								this.Pause));
			}
		},
		// �ƶ���
		MoveTo : function(i) {
			this._slider.style[this._css] = i + "px";
		},
		// ��һ��
		Next : function() {
			this.Run(++this.Index);
		},
		// ��һ��
		Previous : function() {
			this.Run(--this.Index);
		},
		// ֹͣ
		Stop : function() {
			clearTimeout(this._timer);
			this.MoveTo(this._target);
		}
	};
} catch (e) {

}