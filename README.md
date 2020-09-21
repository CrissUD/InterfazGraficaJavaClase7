# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemás de la Universidad Distrital Francisco Jose de Caldas

# Clase 7

## Objetivos

* Comprender el concepto de reutilización de componentes gráficos y las ventajas que provee al desarrollar interfaces gráficas de usuario.
* Reconocer los diferentes enfoques que existen para realizar reutilización de componentes gráficos y en que casos es mejor utilizar alguno de ellos.
* Identificar en que momento es necesario realizar la reutilización de componentes y que enfoque es acorde según la situación presentada.

# Antes de Comenzar

### **Actualización de Imágenes en recursos**
Para continuar con la lección deberá actualizar la carpeta **resources/images** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase7** seguido de **resources/images**.

### **Ajustes en el servicio Recursos Service**

En el servicio **RecursosService** se crea un nuevo objeto decorador **Border**:

**Declaración:**
```javascript
private Border borderGris;
```

**Ejemplificación:**
```javascript
// Dentro del método crearBordes
borderGris = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
```

**Método get:**
```javascript
public Border getBorderGris(){ return borderGris; }
```

Por ultimo se crea un objeto decorador tipo **Color**:

**Declaración:**
```javascript
// Al inicio del servicio
private Color colorGrisClaro;
```

**Ejemplificación:**
```javascript
// Dentro del método crearColores
colorGrisClaro = new Color(249, 246, 249);
```

**Método get:**
```javascript
public Color getColorGrisClaro(){ return colorGrisClaro; }
```

***Nota:** Recordar que estos objetos decoradores dentro del servicio **RecursosService** se crean pensando en que serán utilizados en varias partes del proyecto, si ese no es el caso entonces el objeto decorador debe ser creado unicamente en el componente gráfico donde se necesita.*

### **Recordatorio**

Recordando el recorrido, se tiene la vista principal con una integración de varios componentes gráficos, además existe un control de visibilidad mediante la navegación de componentes. También se reviso la importancia de controlar la creación de objetos de los componentes gráficos.

<div align='center'>
    <img  src='https://i.imgur.com/2F1rXG7.png'>
    <p>Vista Principal y Single-Page App</p>
</div>

# Reutilización de componentes gráficos

En esta sesión se explicará un tema de gran importancia, la reutilización de componentes y en que casos realizar esta practica:

* Reutilizacion de componentes gráficos.
    * Enfoque de reutilización por incorporación.
    * Enfoque de reutilización por posicionamiento.

# Reutilización de componentes gráficos

En la sesión pasada se creo una serie de componentes que pueden ser visible una vez el usuario lo solicite oprimiendo los botones ubicados en el componente **navegacionUsuario**. Algunos de estos componentes gráficos son: **inicio**, **perfil**, **amigos**, **productos** y **configuraciones**. Sin embargo, estos componentes aun no tienen nada más que un color de fondo. 

Esta lección esta enfocada en la construcción el componente gráfico **inicio** haciendo uso de la reutilizacion de otros componentes que se crearán para este propósito.

## Concepto de reutilización

Para entender mejor el concepto de reutilización se va a mostrar primero cual es el diseño deseado al crear el componente gráfico **inicio** a continuación:

<div align='center'>
    <img  src='https://i.imgur.com/6LcW9rb.png'>
    <p>Resultado de construcción de componente gráfico Inicio</p>
</div>

Es posible notar con un pequeño grado de detalle que el componente gráfico **inicio** esta conformado por varios objetos gráficos, tiene algunos paneles, bastantes imágenes, títulos y párrafos. Sin embargo, aumentando el grado de detalle es posible notar que existen ciertas particularidades con los objetos gráficos que conforman al componente gráfico **inicio**.

Primero se observa la parte superior del componente, se puede ver que hay tres paneles y los tres contienen los mismos objetos gráficos, cada uno de ellos tiene una imágen en la parte superior, seguido de un título y un párrafo justificado en la parte inferior.

<div align='center'>
    <img  src='https://i.imgur.com/Ii5Dopv.png'>
    <p>Paneles de la parte superior con un mismo patrón en sus objetos gráficos</p>
</div>

Ahora se observa la parte inferior, se puede notar que hay un panel principal que contiene un conjunto de paneles que están distribuidos uniformemente a lo largo del panel contenedor. Observe que cada uno de estos paneles cuentan con un patrón, todos tienen un borde gris que los separa unos de otros, un icono centrado, seguido de un título y un párrafo centrados de igual forma.

<div align='center'>
    <img  src='https://i.imgur.com/mZFvPJ4.png'>
    <p>Paneles de la parte inferior con un mismo patrón en sus objetos gráficos</p>
</div>

Lo único que cambia en ambos casos es el contenido en cada panel, pero la estructura interna es idéntica. Otro factor importante es que dicha similitud en estructura está conformada por distintos objetos gráficos y aquí está la **clave para usar un componente reutilizable**, para crear un componente que se pueda reutilizar en varias partes este debe estar conformado por varios objetos gráficos que cumplen un patrón de estructura que se desea repetir en la interfaz gráfica.

No vale la pena construir un componente que repita el patrón de un simple objeto gráfico (piense en los botones del componente **navegacionUsuario**), aunque estos botones se conforman de varios elementos (Imágenes y texto) y cumplen con un patron de estructura que se repite, es solo un botón que puede ser construido con el servicio **ObjGraficosService**. Por otro lado si hay un patrón que se repite y vincula a varios objetos gráficos si vale la pena revisar si realizar la reutilización de componentes gráficos.

El componente **inicio** podría escribirse completamente en la clase **Template** sin depender de ningún otro componente para su construcción y no está mal, es una forma de hacerlo. Sin embargo, imagine todo el código repetido que va existir dentro de esta clase para mostrar 3 paneles que tienen una misma estructura y otros 6 paneles que tienen esta misma condición.  Realmente es muy tedioso y llenará la clase **Template** de un extenso código que afectara en la forma en que se analiza, mantiene y se entiende el código.

Una alternativa a esto es la construcción de componentes gráficos que encapsulen el código de este patrón de estructura identificado y se pueda hacer uso de él, las veces que sean necesarias. A continuación se muestra a ver como hacer esto posible. 

Cuando un componente depende de otros componentes para su construcción se crea una relación de **Componente Padre** Y **Componente Hijo** (No tiene que ver con el concepto de herencia) ya que el componente padre contiene al componente hijo. Es común encontrar este tipo de relaciónes, un ejemplo que ya se ha realizado es entre el componente **VistaPrincipal** y los componente **inicio**, **barraTitulo**, **navegacionUsuario** etc. Ahora el mismo componente **inicio** tendrá otros componentes para su construcción y estos serán los hijos de **inicio**, esta construcción estará basada en la reutilización de componentes que a su vez se basará en dos enfoques principales.  

## Construcción base del componente inicio

Se va a construir a continuación la base del componente **inicio**, en su clase **Template** se va a crear unos paneles que sirven de soporte a los componentes a construir y además se crearán ciertos objetos más:

* Primero se obtienen los servicios **ObjGraficosService** y **RecursosService** que serán necesarios en la construcción de algunos objetos gráficos:

**Declaración:**
```javascript
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos; 
```

**Obtención de servicios:**
```javascript
// Dentro del Constructor
sObjGraficos = ObjGraficosService.getService();
sRecursos = RecursosService.getService();
```

* Se proporciona de otro color de fondo al componente:

```javascript
// Dentro del Constructor
this.setBackground(sRecursos.getColorGrisClaro());
```

* Se van a crear paneles base para contener los objetos gráficos que serán visibles en la ventana principal además de un Label que representará el título principal en el panel inferior (**pAcciones**):

**Declaración:**
```javascript
// Declaración Objetos Gráficos 
private JPanel pMision, pVision, pNosotros, pAcciones;
private JLabel lAcciones;
```
**Método crearJPanels:**
```javascript
public void crearJPanels(){
  this.pMision = sObjGraficos.construirJPanel(20, 15, 256, 230, Color.WHITE, null);
  this.add(pMision);

  this.pVision = sObjGraficos.construirJPanel(296, 15, 256, 230, Color.WHITE, null);
  this.add(pVision);
  
  this.pNosotros = sObjGraficos.construirJPanel(572, 15, 256, 230, Color.WHITE, null);
  this.add(pNosotros);

  this.pAcciones = sObjGraficos.construirJPanel(20, 260, 810, 330, Color.WHITE, null);
  this.add(pAcciones);
}
```

**llamada del método dentro del constructor:**
```javascript
// Dentro del constructor
this.crearJPanels();
```

Cuando se ejecuta la aplicación y se oprime el botón que llama al componente **inicio**, la interfaz gráfica luce de la siguiente manera:

<div align='center'>
    <img  src='https://i.imgur.com/JsPvkP3.png'>
    <p>Componente inicio con sus paneles base adicionados</p>
</div>

Para este componente se va a realizar la modularización un tanto distinta, esta vez no se va a separar la creación por tipo de objetos gráficos, **se va a separar la creación por el contenido en cada panel**, con una pequeña excepción en la creación de objetos decoradores, por ahora solo se declaran los métodos:

```javascript
public void crearObjetosDecoradores(){
}
```
```javascript
public void crearContenidoPMision(){
}
```
```javascript
public void crearContenidoPVision(){
}
```
```javascript
public void crearContenidoPNosotros(){
}
```
```javascript
public void crearContenidoPAcciones(){
}
```

# Enfoque de reutilización por incorporación.

Este enfoque se caracteriza por que cuando se crea el **componente hijo**, este va a ser incorporado a un panel que exista previamente en el **componente padre**, por ejemplo, ya fueron creados los paneles **pMision, pVision, pNosotros** en el componente padre. Esto quiere decir que no es necesario preocuparse con la locación del componente hijo, esta ya se configuro cuando se crearon los paneles de incorporación, lo que si se debe tener pendiente es que tanto el panel de incorporación como el componente hijo tengan el mismo tamaño y de esa forma no habrán problemás de posicionamiento. Este enfoque ya se ha realizado en sesiones anteriores, donde **navegaciónUsuario** y **barraTitulo** fueron incorporados a unos paneles creados previamente en **vistaPrincipal**.

Se crea un nuevo component que encapsule la estructura del contenido de los paneles superiores, este componente será llamado **tarjeta**, para esto se crea su respectivo paquete y clases dentro del directorio **components**:

<div align='center'>
    <img  src='https://i.imgur.com/KwTONrs.png'>
    <p>Componente tarjeta creado dentro del paquete components</p>
</div>

* Se empieza con la clase **Component** y como de costumbre se realizan los siguientes pasos:

Como el componente no tiene botones no se va a necesitar la implementación de ninguna interfaz.
```javascript
public class TarjetaComponent{
}
```

Se crea un objeto de la clase **Template** y se realiza la inyección enviándose por argumento la referencia de si mismo con la palabra **this**:

**Declaración:**
```javascript
private TarjetaTemplate tarjetaTemplate;
```

**Ejemplificación:**
```javascript
// Dentro del constructor
tarjetaTemplate = new TarjetaTemplate(this);
```

Se crea el método **get** de su respectivo **Template**:
```javascript
public TarjetaTemplate getTarjetaTemplate(){
  return tarjetaTemplate;
}
```

* Ahora se codifica la clase **Template**:

Esta al representar las características gráficas del componente debe heredar de un **JPanel**:  
```javascript
public class TarjetaTemplate extends JPanel{
}
```

Como se ha realizado anteriormente, se va a obtener los servicios **ObjGraficosService** y **RecursosService** además de obtener por constructor la inyección de la clase **Component**:

**Ejemplificación**
```javascript
private TarjetaComponent tarjetaComponent;
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos;
```

**Obtención de servicios e inyección:**
```javascript
public TarjetaTemplate(TarjetaComponent tarjetaComponent){
  this.tarjetaComponent = tarjetaComponent;
  sObjGraficos= ObjGraficosService.getService();
  sRecursos = 
  RecursosService.getService();
}
```

Ahora se configuran las propiedades gráficas al componente:
```javascript
// Dentro del constructor
this.setSize(256, 230);
this.setBackground(Color.white);
this.setLayout(null);
this.setVisible(true);
```

Note que el tamaño del componente debe ser igual panel al cual será incorporado. En este caso en los paneles **pMision, pVision y pNosotros**.

<div align='center'>
    <img  src='https://i.imgur.com/hGLhBrc.png'>
    <p>Mismo tamaño del componente con los paneles a reemplazar</p>
</div>

Este componente va a contener:
* Label que contiene la imagen.
* Label que contiene el título.
* Label que contiene el párrafo.
* Objeto decorador iDimAux para redimensionar imágenes.

**declaración:**
```javascript
// Declaración Objetos Gráficos
private JLabel lTitulo, lImagen, lParrafo;

// Declaración Objetos Decoradores
private ImageIcon iDimAux;
```

Como el contenido del título, el contenido del párrafo y la imagen van a ser las propiedades que serán dinámicas (que van a variar). Estas deben ser recibidas de alguno modo. Se podría tomar el enfoque de **setters y getters**, sin embargo, se opta por recibir estos parámetros desde el constructor:

<div align='center'>
    <img  src='https://i.imgur.com/NrO68Ne.png'>
    <p>Parámetros recibidos en el constructor</p>
</div>

Se puede notar que ahora el constructor a parte de recibir la **inyección de dependencia** recibe también ahora:
* Un **String** que representa el título.
* Un **ImageIcon** que representa la imágen del componente.
* Un **String** que representa el párrafo.

***Nota:** Como el código de este componente es corto y para evitar la declaración de más atributos en la clase se van a crear los objetos gráficos dentro del constructor:*

**Imagen:**
```javascript
// Dentro del constructor
iDimAux = new ImageIcon(
  iImagen.getImage()
    .getScaledInstance(246, 110, Image.SCALE_AREA_AVERAGING)
);
lImagen = sObjGraficos.construirJLabel(
  null, 
  5, 5, 246, 110, 
  sRecursos.getCMano(),
  iDimAux, 
  null, null, null, null,
  "c"
);
this.add(lImagen);
```
Se puede observar que la imágen que redimensiona la variable auxiliar **iDimAux** es la que fue recibida por parámetro el constructor.

**título:**
```javascript
// Dentro del constructor
this.lTitulo = sObjGraficos.construirJLabel(
  titulo,
  15, 120, 180, 30,
  null, null,
  sRecursos.getFontTitulo(),
  null,
  sRecursos.getColorAzul(),
  null,
  "l"
);
this.add(lTitulo);
```

Se puede observar que el texto que se envía para la construcción del Label es el String **título** recibido por parámetro desde el constructor.

**Párrafo:**
```javascript
// Dentro del constructor
lParrafo = sObjGraficos.construirJLabel(
  "<html><div align='justify'>" + parrafo + "</div></html>", 
  20, 120, 206, 120, 
  null, null,
  sRecursos.getFontLigera(), 
  null, 
  sRecursos.getColorGrisOscuro(), 
  null,
  "c"
);
this.add(lParrafo);
```
Se puede observar que el texto que se envía para la construcción del Label es el String **parrafo** recibido por parámetro desde el constructor. También se puede observar que el párrafo está contenido dentro de etiquetas html. Esto se realiza para darle la estructura de texto justificado ya que es el componente quien se debe encargar de la estructura.

En teoría el componente esta listo, sin embargo, ahora el editor de texto indica que hay un error en la clase **Component** justamente en la ejemplificación de la clase **Template**, esto es debido a que ahora por constructor se están pidiendo más parámetros y como argumento solo se esta enviando la **inyección**.

<div align='center'>
    <img  src='https://i.imgur.com/P1JVTR5.png'>
    <p>Error en clase Component ya que se piden más parámetros</p>
</div>

Para resolver esto se debe recibir por parámetro en el constructor de la clase **Component** los mismos parámetros (**título, Imagen, Párrafo**), para después pasarlos a su clase **Template**:
```javascript
public TarjetaComponent(String titulo, ImageIcon iImagen, String parrafo) {
  tarjetaTemplate = new TarjetaTemplate(this, titulo, iImagen, parrafo);
}
```

El componente **Tarjeta** esta listo para ser incorporado, se debe hacer dicha incorporación en el componente **inicio**. En la clase **InicioTemplate** se van a declarar las imágenes necesarias para los componentes, luego se ejemplifican:

**Declaración:**
```javascript
private ImageIcon iTarjeta1, iTarjeta2, iTarjeta3;
```

**Ejemplificación:**
```javascript
// Dentro del método crearObjetosDecoradores
this.iTarjeta1 = new ImageIcon("clase7/resources/images/tarjeta1.jpg");
this.iTarjeta2 = new ImageIcon("clase7/resources/images/tarjeta2.jpg");
this.iTarjeta3 = new ImageIcon("clase7/resources/images/tarjeta3.jpg");
```

Note algo importante, aunque es el componente **tarjeta** el que va a mostrar las imágenes en pantalla estas van a ser creadas de su componente padre que es **inicio** en este caso. Ya que el componente **tarjeta** exige unas imágenes como parámetros y estas deben ser creadas previamente. Sin embargo el componente **Tarjeta** si se encarga de la redimensión en las imágenes.

Se va a realizar la incorporación de este componente primero en el método **crearContenidoPMision**, como este enfoque es **por medio de incorporación** lo que se debe realizar es adicionar el componente al panel que incorporará el componente gráfico:

* Se llama al panel en cuestión y se indica que se añadirá un objeto gráfico con el método **add**:
```javascript
public void crearContenidoPMision() {
  this.pMision.add();
}
```

* Dentro de los paréntesis se puede crear una **Ejemplificación anónima** del componente:
```javascript
public void crearContenidoPMision() {
    this.pMision.add(new TarjetaComponent());
}
```

* Sin embargo la clase **Component** pide por parámetros 3 cosas **(String título, ImageIcon imagen, String párrafo)** asi que se debe enviar por argumento lo que se pide:
```javascript
public void crearContenidoPMision() {
  this.pMision.add(
    new TarjetaComponent(
      "Nuestra Misión", 
      iTarjeta1, 
      "Brindar cursos a la comunidad académica para" + 
      "complementar habilidades fuera del pensum común." 
    )
  );
}
```

Hasta el momento la incorporación esta fallando y es que es necesario recordar que la clase **Component** no cuenta con características gráficas, es la clase **Template** la que las tiene asi que se debe obtener mediante el método **get** de la clase **Component**.

```javascript
public void crearContenidoPMision() {
  this.pMision.add(
    new TarjetaComponent(
      "Nuestra Misión", 
      iTarjeta1, 
      "Brindar cursos a la comunidad académica para" + 
      "complementar habilidades fuera del pensum común." 
    ).getTarjetaTemplate()
  );
}
```

* Ahora el componente ha quedado incorporado, solo falta llamar al método **crearContenidoPMision** desde el constructor para que el contenido pueda ser visible:
```javascript
// Dentro del constructor
this.crearObjetosDecoradores();
this.crearContenidoPMision();
```
***Nota:** También faltaba llamar al método **crearObjetosDecoradores** dentro del constructor.*

Si se corre la aplicación y se oprime el botón que muestra el componente **inicio** la interfaz se ve asi:

<div align='center'>
    <img  src='https://i.imgur.com/CuhXYvJ.png'>
    <p>Interfaz gráfica con la llamada del componente tarjeta 1 vez</p>
</div>

Ahora se realiza el mismo proceso con los otros 2 paneles, claramente el contenido será distinto asi que los argumentos enviados al componente también lo serán:

**Panel pVision:**
```javascript
public void crearContenidoPVision() {
  this.pVision.add(
    new TarjetaComponent(
      "Nuestra Visión", 
      iTarjeta2, 
      "Brindar cursos académicos al 80% de los" +
      "estudiantes de ingeniería de Sistemás." 
    ).getTarjetaTemplate()
  );
}
```

**Panel pNosotros:**
```javascript
public void crearContenidoPNosotros() {
  this.pNosotros.add(
    new TarjetaComponent(
      "Sobre Nosotros", 
      iTarjeta3, 
      "Somos un grupo de trabajo de la Universidad" +
      "distrital Francisco jose de Caldas."
    ).getTarjetaTemplate()
  );
}
```

**llamada de métodos dentro del constructor**
```javascript
// Dentro del constructor
this.crearContenidoPVision();
this.crearContenidoPNosotros(); 
```

Ejecutando la aplicación nuevamente ahora la interfaz se ve asi:

<div align='center'>
    <img  src='https://i.imgur.com/0wADZUn.png'>
    <p>Componente inicio con la reutilización del componente Tarjeta</p>
</div>

# Enfoque de reutilización por Posicionamiento.

A menudo existen casos donde se requiere un componente reutilizable una n cantidad de veces y no se tiene con certeza el numero de veces que se va a reutilizar, un ejemplo puede ser una lista de películas obtenidas externamente donde cada película va a ser un componente hijo, sin embargo podría ser un listado largo de películas que se va a mostrar y no se sabe cuantas sean exactamente. Por lo que crear paneles base para incorporar cada componente hijo es una mala idea, una buena opción es traer el componente gráfico hijo y posicionarlo dentro de un espacio dado. 

Este enfoque se caracteriza por que cuando se cree el **componente hijo**, este no va se va incorporar a ningún objeto gráfico directamente sino que va a ocupar un espacio dentro del **componente padre**. Por lo que ahora se debe tener presente la locación del componente aunque por otro lado ya no es una preocupación que el componente hijo tenga el mismo tamaño de algún objeto gráfico previo ya que al no incorporar nada esta libre de tener un tamaño independiente.

Se crea un nuevo component que encapsule la creación del contenido de los paneles inferiores, este componente será llamado **accion**, para esto se crea su respectivo paquete y clases dentro del directorio **components**:

<div align='center'>
    <img  src='https://i.imgur.com/hiWKgTt.png'>
    <p>Componente accion creado dentro del paquete components</p>
</div>

* Se empieza con la clase **Component** y como de costumbre se realizan los siguientes pasos:

Como el componente no tiene botones no va a necesitar la implementación de ninguna interfaz.
```javascript
public class AccionComponent {
}
```

Se crea un objeto de la clase **Template** y se realiza la inyección enviándose por argumento la referencia de si mismo con la palabra **this**:

**Declaración:**
```javascript
private AccionTemplate accionTemplate;
```

**Ejemplificación:**
```javascript
// Dentro del constructor
this.accionTemplate= new AccionTemplate(this);
```

Se crea su método **get** de su respectivo **Template**:
```javascript
public AccionTemplate getAccionTemplate(){
  return accionTemplate;
}
```

* Ahora se va a codificar la clase **Template**:

Esta al representar las características gráficas del componente debe heredar de un **JPanel**:  
```javascript
public class AccionTemplate extends JPanel {
}
```

Se obtienen los servicios **ObjGraficosService** y **RecursosService** además de obtener por constructor la inyección de la clase **Component**:

**Ejemplificación**
```javascript
private AccionComponent accionComponent;
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos;
```

**Obtención de servicios e inyección:**
```javascript
public AccionTemplate(AccionComponent accionComponent){
  this.accionComponent = accionComponent;
  sObjGraficos= ObjGraficosService.getService();
  sRecursos = RecursosService.getService();
}
```

Ahora se configuran las propiedades gráficas al componente:
```javascript
// Dentro del constructor
this.setSize(250, 125);
this.setBackground(Color.WHITE);
this.setBorder(sRecursos.getBorderGris());
this.setLayout(null);
this.setVisible(true);
```

Note que el componente tiene un borde que se llama del servicio **RecursosService** para poder diferenciarse. Además el tamaño del componente no tiene la restricción de ser igual a ningún objeto gráfico del componente padre ya que se usará un enfoque de **posicionamiento**.

Este componente va a contener:
* Label que contiene el título.
* Label que contiene el icono.
* Label que contiene el párrafo.
* Objeto decorador iDimAux para redimensionar imágenes.

**declaración:**
```javascript
// Declaración Objetos Gráficos
private JLabel lImagen, lTitulo, lParrafo;

// Declaración Objetos Decoradores
private ImageIcon iDimAux;
```

Como el contenido del título, el contenido del párrafo y el icono van a ser las propiedades que serán dinámicas (que van a variar). Estas deben ser recibidas de alguno modo. Nuevamente se opta por recibir estos parámetros desde el constructor:

<div align='center'>
    <img  src='https://i.imgur.com/f1oncLQ.png'>
    <p>Parámetros recibidos en el constructor</p>
</div>

Se puede notar que ahora el constructor a parte de recibir la **inyección de dependencia** recibe también ahora:
* Un **ImageIcon** que representa el icono del componente.
* Un **String** que representa el título.
* Un **String** que representa el párrafo.

***Nota:** Como el código de este componente también es corto y para evitar la declaración de más atributos en la clase se crean los objetos gráficos dentro del constructor:*

**Imagen:**
```javascript
// Dentro del constructor
iDimAux = new ImageIcon(
  imagen.getImage()
    .getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING)
);
this.lImagen = sObjGraficos.construirJLabel(
  null, 
  (250 - 60) / 2, 5, 45, 45,
  null,
  iDimAux, 
  null, null, null, null,
  "c"
);
this.add(lImagen);
```
Se puede observar que la imagen que redimensiona la variable auxiliar **iDimAux** es la que fue recibida por parámetro en el constructor.

**título:**
```javascript
// Dentro del constructor
this.lTitulo = sObjGraficos.construirJLabel(
  titulo,
  (250 - 220) / 2, 50, 220, 30,
  null, null,
  sRecursos.getFontTitulo(),
  null,
  sRecursos.getColorGrisOscuro(),
  null,
  "c"
);
this.add(lTitulo);
```

Se puede observar que el texto que se envía para la construcción del Label es el String **título** recibido como parámetro desde el constructor.

**Párrafo:**
```javascript
// Dentro del constructor
this.lParrafo = sObjGraficos.construirJLabel(
  "<html><div align='center'>" + parrafo + "</div></html>",
  (250 - 230) / 2, 75, 230, 50, 
  null, null,
  sRecursos.getFontLigera(), 
  null, 
  sRecursos.getColorGrisOscuro(),
  null,
  "c"
);
this.add(lParrafo);
```
Se puede observar que el texto que se envía para la construcción del Label es el String **parrafo** recibido como parámetro desde el constructor. Además el parrafo esta envuelto en etiquetas HTML que le proporcionan una estructura al texto de estar centrado. Recordar que este componente se debe encargar de encapsular aspectos de estructura como este.

Como se vio con el anterior componente **tarjeta** la clase **Component** ahora debe ser modificada ya que la clase **Template** exige nuevos parámetros que deben ser enviados como argumento.

Para resolver esto se debe recibir por parámetro en el constructor de la clase **Component** los mismos parámetros (**Imagen, titulo, Párrafo**), para después pasárselos a su clase **Template**:
```javascript
public AccionComponent(ImageIcon imagen, String titulo, String parrafo) {
    this.accionTemplate= new AccionTemplate(this, imagen, titulo, parrafo);
}
```

El componente gráfico **Accion** esta listo para usarse ahora se explica de que manera se reutilizará desde su componente padre.

En la clase **InicioTemplate** primero se van a declarar las imágenes necesarias para los componentes, luego se ejemplifican:

**Declaración:**
```javascript
private ImageIcon iClase, iPantalla, iIdea, iCelular, iEstadistica, iDireccion; 
```

**Ejemplificación:**
```javascript
// Dentro del método crearObjetosDecoradores
this.iClase = new ImageIcon("clase7/resources/images/clases.png");
this.iPantalla = new ImageIcon("clase7/resources/images/pantalla.png");
this.iCelular = new ImageIcon("clase7/resources/images/celular.png");
this.iIdea = new ImageIcon("clase7/resources/images/ideas.png");
this.iEstadistica = new ImageIcon("clase7/resources/images/estadisticas.png");
this.iDireccion = new ImageIcon("clase7/resources/images/direccion.png");
```
* Previo a la llamada del componente gráfico **Accion** se va crear un título al panel inferior **pAcciones**:
```javascript
public void crearContenidoPAcciones(){
  this.lAcciones = sObjGraficos.construirJLabel(
    "Nuestros Servicios",
    10, 10, 160, 30,
    null, null,
    sRecursos.getFontTitulo(),
    null,
    sRecursos.getColorAzul(),
    null,
    "c"
  );
  this.pAcciones.add(lAcciones);
}
```

Como esta vez no se va a incorporar ningún componente no es posible crear la ejemplificación de forma anónima, es necesario crear variables de objeto para representar al componente hijo:
```javascript
// Dentro del método crearContenidoPAcciones
AccionTemplate p1= new AccionComponent();
```
Se debe recordar que:
* la clase **AccionComponent** exige el envío de algunos argumentos.
* Se debe obtener la clase **Template** del componente ya que el objeto que se debe agregar debe contener características gráficas y se declaro inicialmente como un **ActionTemplate**.

```javascript
// COMPONENTE ACCIÓN 1 ------------------------------------
AccionTemplate p1= new AccionComponent(
  iClase, 
  "Clases", 
  "Clases a la comunidad que complementan el pensum."
).getAccionTemplate();
```

***Nota:** Puede observar que se crea un objeto tipo Template del Componente Accion pero se esta igualando a la ejemplificación de la clase Component, esto claramente generaría error pero esto se hace por que inmediatamente en la ejemplificación se va a traer la clase template a traves del método **getAccionTemplate()** y asi la igualdad será cierta.*

Ya se tiene un objeto del componente hijo dentro de **inicio** pero aun se debe indicar la posición y además realizar la agregación en el panel inferior **pAcciones**:
```javascript
p1.setLocation(15, 50);
this.pAcciones.add(p1);
```

Para probar el método se llama en el constructor:

```javascript
// Dentro del constructor
this.crearContenidoPAcciones();
```

Al ejecutar la aplicación, la interfaz luce asi:

<div align='center'>
    <img  src='https://i.imgur.com/2pxfy1H.png'>
    <p>Componente inicio con la agregación de un componente accion</p>
</div>

este proceso se repite varias veces más:
```javascript
// COMPONENTE ACCIÓN 2 ------------------------------------
AccionTemplate p2 = new AccionComponent(
  iPantalla, 
  "Clases Virtuales", 
  "Cursos virtuales como medio de enseñanza."
).getAccionTemplate();
p2.setLocation(30 + p2.getWidth(), 50);
this.pAcciones.add(p2);

// COMPONENTE ACCIÓN 3 ------------------------------------
AccionTemplate p3 = new AccionComponent(
  iIdea, 
  "Generación de ideas", 
  "Desarrollo de ideas con tecnologías actuales."
).getAccionTemplate();
p3.setLocation(45 + p3.getWidth() * 2, 50);
this.pAcciones.add(p3);

// COMPONENTE ACCIÓN 4 ------------------------------------
AccionTemplate p4 = new AccionComponent(
  iCelular, 
  "Notificaciones", 
  "Notificaión el estado de tus cursos y actividades."
).getAccionTemplate();
p4.setLocation(15, 65 + p4.getHeight());
this.pAcciones.add(p4);

// COMPONENTE ACCIÓN 5 ------------------------------------
AccionTemplate p5 = new AccionComponent(
  iEstadistica, 
  "Estadisticas", 
  "Gestión de participación en nuestros cursos."
).getAccionTemplate();
p5.setLocation(30 + p5.getWidth(), 65 + p5.getHeight());
this.pAcciones.add(p5);

// COMPONENTE ACCIÓN 6 ------------------------------------
AccionTemplate p6 = new AccionComponent(
  iDireccion, 
  "Dirección", 
  "Damos direcciónamiento a nuestros estudiantes."
).getAccionTemplate();
p6.setLocation(45 + p6.getWidth() * 2, 65 + p6.getHeight());
this.pAcciones.add(p6);
```

Corriendo la aplicación es posible ver el resultado propuesto desde el comienzo:

<div align='center'>
    <img  src='https://i.imgur.com/HQUhJfh.png'>
    <p>Vista Principal con el panel inicio terminado</p>
</div>

El anterior enfoque queda un tanto desperdiciado debido a que se esta repitiendo el código las 6 veces que fue reutilizado el componente. Si existieran 10 acciones más se tendría que volver a repetir este código y no es para nada optimo hacer esto. Un enfoque apropiado es crear un arreglo de objetos donde cada objeto contenga la información necesaria (Imagen, titulo, Párrafo) y recorrerlo mediante un ciclo para que de esta forma el código solo sea escrito una sola vez y asi ahorrar lineas de código. Sin embargo este enfoque se discutirá en futuras clases cuando se hable acerca de **Servicios Lógicos**.

## Pequeña Reflexión de la reutilización

El concepto de reutilización es quizás el factor que más le da reconocimiento al uso de los componentes gráficos, de esta forma no solo se tiene un código mucho más entendible y organizado. También se esta encapsulado la estructura de una plantilla dentro de un componente lo cual dota del código de una estructuración y modularizacion alta. Incluso el concepto de reutilización puede ser tan util  que si por ejemplo en algún otro componente como puede ser **Productos** se quiere usar uno de estos componentes reutilizables perfectamente se puede hacer haciendo de su función mucho más amplia para el proyecto. 

Otro criterio que podría tomarse a favor es el dinamismo hacia el tamaño y posición de objetos gráficos dentro de la estructura de un componente altamente reutilizable, piense por un momento, que tal si se quiere usar de nuevo el componente **tarjeta** en otra parte del proyecto pero esta vez con más altura o menos ancho, podría entonces el componente pedir el ancho y alto por parámetros para incorporarlo o posicionarlo en la interfaz. Además se puede usar un posicionamiento y tamaño de los objetos gráficos internos basado en **porcentajes** para que no se pierda la estructura deseada.

Este posicionamiento basado en **porcentajes** es un enfoque que mejora el enfoque de posicionamiento en pixeles pero esta basado en el. En este curso no veremos dicho enfoque pero se menciona para motivar a la investigación al estudiante de este curso.

# Resultado

Si has llegado hasta aquí **!Felicidades!** se ha aprendido a realizar reutilización de componentes gráficos, cuando utilizarse y los diferentes enfoques de **incorporación y posicionamiento** para encapsular la estructura de una plantilla en un componente que se puede usar varias veces.

En la siguiente clase se revisará de nuevo el tema de **Eventos** y esta vez vamos a estudiar los eventos del **Mouse**.

# Actividad

Utilizar reutilización de componentes en sus proyectos para encapsular la lógica y estructura de una plantilla que pueda utilizarse varias veces.