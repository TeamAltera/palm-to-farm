import _extends from 'babel-runtime/helpers/extends';
import _classCallCheck from 'babel-runtime/helpers/classCallCheck';
import _createClass from 'babel-runtime/helpers/createClass';
import _possibleConstructorReturn from 'babel-runtime/helpers/possibleConstructorReturn';
import _inherits from 'babel-runtime/helpers/inherits';
import _map from 'lodash/map';
import _invoke from 'lodash/invoke';

import PropTypes from 'prop-types';
import React from 'react';

import { AutoControlledComponent as Component, createPaginationItems, customPropTypes, getUnhandledProps, META } from '../../lib';
import Menu from '../../collections/Menu';
import PaginationItem from './PaginationItem';

/**
 * A component to render a pagination.
 */

var Pagination = function (_Component) {
  _inherits(Pagination, _Component);

  function Pagination() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Pagination);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Pagination.__proto__ || Object.getPrototypeOf(Pagination)).call.apply(_ref, [this].concat(args))), _this), _this.handleItemClick = function (e, _ref2) {
      var nextActivePage = _ref2.value;
      var prevActivePage = _this.state.activePage;

      // Heads up! We need the cast to the "number" type there, as `activePage` can be a string

      if (+prevActivePage === +nextActivePage) return;

      _this.trySetState({ activePage: nextActivePage });
      _invoke(_this.props, 'onPageChange', e, _extends({}, _this.props, { activePage: nextActivePage }));
    }, _this.handleItemOverrides = function (active, type, value) {
      return function (predefinedProps) {
        return {
          active: active,
          type: type,
          key: type + '-' + value,
          onClick: function onClick(e, itemProps) {
            _invoke(predefinedProps, 'onClick', e, itemProps);
            _this.handleItemClick(e, itemProps);
          }
        };
      };
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Pagination, [{
    key: 'render',
    value: function render() {
      var _this2 = this;

      var _props = this.props,
          ariaLabel = _props['aria-label'],
          boundaryRange = _props.boundaryRange,
          siblingRange = _props.siblingRange,
          totalPages = _props.totalPages;
      var activePage = this.state.activePage;


      var items = createPaginationItems({ activePage: activePage, boundaryRange: boundaryRange, siblingRange: siblingRange, totalPages: totalPages });
      var rest = getUnhandledProps(Pagination, this.props);

      return React.createElement(
        Menu,
        _extends({}, rest, { 'aria-label': ariaLabel, pagination: true, role: 'navigation' }),
        _map(items, function (_ref3) {
          var active = _ref3.active,
              type = _ref3.type,
              value = _ref3.value;
          return PaginationItem.create(_this2.props[type], {
            defaultProps: {
              content: value,
              value: value
            },
            overrideProps: _this2.handleItemOverrides(active, type, value)
          });
        })
      );
    }
  }]);

  return Pagination;
}(Component);

Pagination.autoControlledProps = ['activePage'];
Pagination.defaultProps = {
  'aria-label': 'Pagination Navigation',
  boundaryRange: 1,
  ellipsisItem: '...',
  firstItem: {
    'aria-label': 'First item',
    content: '«'
  },
  lastItem: {
    'aria-label': 'Last item',
    content: '»'
  },
  nextItem: {
    'aria-label': 'Next item',
    content: '⟩'
  },
  pageItem: {},
  prevItem: {
    'aria-label': 'Previous item',
    content: '⟨'
  },
  siblingRange: 1
};
Pagination._meta = {
  name: 'Pagination',
  type: META.TYPES.ADDON
};
Pagination.Item = PaginationItem;
Pagination.handledProps = ['activePage', 'aria-label', 'boundaryRange', 'defaultActivePage', 'ellipsisItem', 'firstItem', 'lastItem', 'nextItem', 'onPageChange', 'pageItem', 'prevItem', 'siblingRange', 'totalPages'];
export default Pagination;
Pagination.propTypes = process.env.NODE_ENV !== "production" ? {
  /** A pagination item can have an aria label. */
  'aria-label': PropTypes.string,

  /** Initial activePage value. */
  defaultActivePage: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),

  /** Index of the currently active page. */
  activePage: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),

  /** Number of always visible pages at the beginning and end. */
  boundaryRange: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),

  /** A shorthand for PaginationItem. */
  ellipsisItem: customPropTypes.itemShorthand,

  /** A shorthand for PaginationItem. */
  firstItem: customPropTypes.itemShorthand,

  /** A shorthand for PaginationItem. */
  lastItem: customPropTypes.itemShorthand,

  /** A shorthand for PaginationItem. */
  nextItem: customPropTypes.itemShorthand,

  /** A shorthand for PaginationItem. */
  pageItem: customPropTypes.itemShorthand,

  /** A shorthand for PaginationItem. */
  prevItem: customPropTypes.itemShorthand,

  /**
   * Called on change of an active page.
   *
   * @param {SyntheticEvent} event - React's original SyntheticEvent.
   * @param {object} data - All props.
   */
  onPageChange: PropTypes.func,

  /** Number of always visible pages before and after the current one. */
  siblingRange: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),

  /** Total number of pages. */
  totalPages: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired
} : {};