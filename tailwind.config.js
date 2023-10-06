module.exports = {
  content: ["./src/main/resources/templates/**/*.html}","./src/main/resources/js/*.js}"
  ,"./src/main/resources/templates/*.html}"
  ],
  safelist: [
    'underline'
  ],
  theme: {
    extend: {},
    container: {
      center: true,
    }
  },
  plugins: [
    require('tailwindcss'),
    require('autoprefixer'),
    require('@tailwindcss/forms')
  ]
}
